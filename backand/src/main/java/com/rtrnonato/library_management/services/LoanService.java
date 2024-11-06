package com.rtrnonato.library_management.services;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.entities.LoanItem;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.requests.UpdateLoanRequest;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
     */
	@Transactional
	public Loan createLoan(List<Long> bookIds, Long userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

	    Loan loan = new Loan();
	    loan.setUser(user);  
	    loan.setLoan(LocalDate.now()); 
	    loan.setLoanStatus(LoanStatus.BORROWED);

	    for (Long bookId : bookIds) {
	        Book book = bookRepository.findById(bookId)
	                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + bookId));

	        if (book.getAvailable() <= 0) {
	            throw new IllegalArgumentException("Book with ID " + bookId + " is not available for loan.");
	        }

	        book.decrementAvailable();
	        bookRepository.save(book); 
        
	        LoanItem loanItem = new LoanItem(book, loan, LocalDate.now().plusDays(60));
	        loan.getItems().add(loanItem);
	    }

	    loanRepository.save(loan);
	    return loan;
	}
	
	/**
     * Retorna os empréstimos especificados, atualizando seu status e a disponibilidade dos livros.
     *
     * @param loanIds Os IDs dos empréstimos a serem devolvidos
     * @throws IllegalArgumentException Se algum dos empréstimos especificados não estiver atualmente emprestado
     */
	@Transactional
	public void returnBooks(List<Long> loanIds) {
		for (Long loanId : loanIds) {
			Loan loan = loanRepository.findById(loanId)
					.orElseThrow(() -> new NoSuchElementException("Loan not found with ID: " + loanId));

			if (loan.getLoanStatus() != LoanStatus.BORROWED) {
				throw new IllegalArgumentException("Loan with ID " + loanId + " is not currently borrowed.");
			}

			loan.setLoanStatus(LoanStatus.DELIVERED);
			loan.setDevolution(LocalDate.now());

			System.out.println("The loan " + loanId + " status is now DELIVERED");

			for (LoanItem loanItem : loan.getItems()) {
				Book book = loanItem.getBook();
				
				if (book != null) {
					book.incrementAvailable();
					bookRepository.save(book);
					System.out.println("Book " + book.getId() + " incremented successfully. New available count: "
							+ book.getAvailable());
				} else {
					throw new NoSuchElementException("Book is null for LoanItem with ID: " + loanItem.getId());
				}
				
			}

			loanRepository.save(loan);
			System.out.println("Loan status after save: " + loan.getLoanStatus());
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
     * @param loanData O objeto de empréstimo contendo os dados atualizados
     * @return O empréstimo atualizado
     * @throws ResourceNotFoundException Se o empréstimo especificado não for encontrado
     */
	public Loan updateLoan(Long loanId, UpdateLoanRequest loanData) {
		try {
		    Loan entity = loanRepository.getReferenceById(loanId);
			User user = userRepository.findById(loanData.getUserId()).orElseThrow(
					() -> new ResourceNotFoundException("User not found with ID: " + loanData.getUserId()));
			
			entity.setLoan(loanData.getLoan());
			entity.setDevolution(loanData.getDevolution());
			entity.setUser(user);
			entity.setLoanStatus(LoanStatus.valueOf(loanData.getLoanStatus()));

		    return loanRepository.save(entity);
		} catch(EntityNotFoundException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException(loanId);
		}
	}
	
	 /**
     * Retorna a contagem total de empréstimos.
     *
     * @return ResponseEntity contendo a contagem total de empréstimos.
     */
    @GetMapping("/count")
    @Operation(summary = "Contagem de empréstimos", description = "Retorna o número total de empréstimos")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
	})
	public long countLoans() {
        return loanRepository.count();
    }
}