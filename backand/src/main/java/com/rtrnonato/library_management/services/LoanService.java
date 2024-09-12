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

/**
 * Classe de serviço para operações relacionadas a empréstimos.
 */
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

	/**
     * Recupera todos os empréstimos no sistema.
     *
     * @return Lista de todos os empréstimos
     */
	public List<Loan> findAll() {
		return loanRepository.findAll();
	}

	/**
     * Recupera um empréstimo pelo seu ID.
     *
     * @param id O ID do empréstimo a ser recuperado
     * @return O empréstimo com o ID fornecido
     * @throws NoSuchElementException Se nenhum empréstimo com o ID fornecido for encontrado
     */
	public Loan findById(Long id) {
		Optional<Loan> obj = loanRepository.findById(id);
		return obj.orElseThrow(() -> new NoSuchElementException("Loan not found with ID: " + id));
	}

	/**
     * Cria um novo empréstimo para o usuário e livros especificados.
     *
     * @param bookIds Os IDs dos livros a serem emprestados
     * @param userId  O ID do usuário que está pegando os livros emprestados
     * @return O empréstimo criado
     * @throws NoSuchElementException    Se o usuário ou algum dos livros especificados não forem encontrados
     * @throws IllegalArgumentException Se algum dos livros especificados não estiver disponível para empréstimo
     * @throws IllegalStateException    Se nenhum item de empréstimo for criado
     */
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
	
	/**
     * Retorna os empréstimos especificados, atualizando seu status e a disponibilidade dos livros.
     *
     * @param loanIds Os IDs dos empréstimos a serem devolvidos
     * @throws IllegalArgumentException Se algum dos empréstimos especificados não estiver atualmente emprestado
     */
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
	
	/**
     * Exclui os empréstimos especificados.
     *
     * @param loanIds Os IDs dos empréstimos a serem excluídos
     * @throws ResourceNotFoundException Se algum dos empréstimos especificados não for encontrado
     */
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
	
	/**
     * Atualiza o empréstimo especificado com os dados fornecidos.
     *
     * @param loanId O ID do empréstimo a ser atualizado
     * @param obj    O objeto de empréstimo contendo os dados atualizados
     * @return O empréstimo atualizado
     * @throws ResourceNotFoundException Se o empréstimo especificado não for encontrado
     */
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
	
	/**
     * Atualiza os dados de um empréstimo existente com base nos dados fornecidos.
     *
     * @param entity O empréstimo existente
     * @param obj    O empréstimo com os dados atualizados
     */
	private void updateData(Loan entity, Loan obj) {
		entity.setLoan(obj.getLoan());
		entity.setDevolution(obj.getDevolution());
		entity.setUser(obj.getUser());
		entity.setBook(obj.getBook());
		entity.setItems(obj.getItems());
		entity.setLoanStatus(obj.getLoanStatus());
	}
	
	public long countLoans() {
        return loanRepository.count();
    }
}