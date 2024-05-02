package com.rtrnonato.library_management.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.entities.LoanItem;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.LoanItemRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoanItemRepository loanItemRepository;

	public List<Loan> findAll() {
		return loanRepository.findAll();
	}

	public Loan findById(Long id) {
		Optional<Loan> obj = loanRepository.findById(id);
		return obj.orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
	}

	public Loan createLoan(List<Long> bookIds, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

		Loan loan = new Loan();
		loan.setUser(user);
		loan.setLoan(LocalDate.now());
		loan.setLoanStatus(LoanStatus.BORROWED);
		Set<LoanItem> loanItems = new HashSet<>();
		Set<Book> books = new HashSet<>();
		for (Long bookId : bookIds) {
			Book book = bookRepository.findById(bookId)
					.orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + bookId));

			if (book.getAvailable() <= 0) {
				throw new IllegalArgumentException("Book with ID " + bookId + " is not available for loan.");
			}

			book.decrementAvailable();
			bookRepository.save(book);
			books.add(book);

			loan.setBook(books);
			loanRepository.save(loan);

			LoanItem loanItem = new LoanItem();
			loanItem.setBook(book);
			loanItem.setLoan(loan);
			loanItem.setExpectedReturn(LocalDate.now().plusDays(30));

			loanItems.add(loanItem);
			loanItemRepository.save(loanItem);
		}

		loan.setItems(loanItems);

		if (!loanItems.isEmpty()) {
			return loan;
		} else {
			throw new IllegalStateException("No loan created");
		}
	}

	public void returnBooks(List<Long> loanIds) {
		for (Long loanId : loanIds) {
			Loan loan = loanRepository.findById(loanId)
					.orElseThrow(() -> new NoSuchElementException("Loan not found with ID: " + loanId));

			if (loan.getLoanStatus() != LoanStatus.BORROWED) {
				throw new IllegalArgumentException("Loan with ID " + loanId + " is not currently borrowed.");
			}

			loan.setLoanStatus(LoanStatus.DELIVERED);

			System.out.println("the loan" + loanId + "status is now DELIVERED");

			loan.setDevolution(LocalDate.now());

			for (LoanItem loanItem : loan.getItems()) {
				Book book = loanItem.getBook();
				book.incrementAvailable();
				loanItem.setActualReturn(LocalDate.now());
				bookRepository.save(book);
			}

			loanRepository.save(loan);
			
			System.out.println(loan.getLoanStatus());
		}
	}
	
	public void deleteLoan(List<Long> loanIds) {
		try {
			for (Long loanId : loanIds) {
				if (!loanRepository.existsById(loanId)) {
				throw new ResourceNotFoundException(loanIds);
				}
				loanRepository.deleteById(loanId);
			}
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(loanIds);
		}
	}
	
	public Loan updateLoan(Long loanId, Loan obj) {
		try {
		    Loan entity = loanRepository.getReferenceById(loanId);
		    updateData(entity, obj);
		    return loanRepository.save(entity);
		} catch(EntityNotFoundException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException(loanId);
		}
	}
	
	private void updateData(Loan entity, Loan obj) {
		entity.setLoan(obj.getLoan());
		entity.setDevolution(obj.getDevolution());
		entity.setUser(obj.getUser());
		entity.setBook(obj.getBook());
		entity.setItems(obj.getItems());
		entity.setLoanStatus(obj.getLoanStatus());
	}
}
