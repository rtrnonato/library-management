package com.rtrnonato.library_management.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
	
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
	    
	    List<Loan> loans = new ArrayList<>();
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
	        
	        Loan loan = new Loan();
	        loan.setBook(books);
	        loan.setUser(user);
	        loan.setLoan(LocalDate.now());
	        loan.setLoanStatus(LoanStatus.BORROWED);
	        
	        loans.add(loanRepository.save(loan));
	    }
	    
	    if (!loans.isEmpty()) {
	        return loans.get(0);
	    } else {
	        throw new IllegalStateException("No loans created");
	    }
	}

	
}
