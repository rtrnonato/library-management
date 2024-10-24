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
import org.springframework.transaction.annotation.Transactional;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.entities.LoanItem;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import com.rtrnonato.library_management.entities.pk.LoanItemPK;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.LoanItemRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.requests.UpdateLoanRequest;
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
	@Transactional
	public Loan createLoan(List<Long> bookIds, Long userId) {
	    // Busca o usuário pelo ID
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

	    // Cria um novo empréstimo
	    Loan loan = new Loan();
	    loan.setUser(user);  // Associa o usuário ao empréstimo
	    loan.setLoan(LocalDate.now());  // Define a data atual como a data do empréstimo
	    loan.setLoanStatus(LoanStatus.BORROWED);  // Define o status do empréstimo como BORROWED

	    // Itera sobre os IDs dos livros para processar cada livro
	    for (Long bookId : bookIds) {
	        // Busca o livro pelo ID
	        Book book = bookRepository.findById(bookId)
	                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + bookId));

	        // Verifica se o livro está disponível
	        if (book.getAvailable() <= 0) {
	            throw new IllegalArgumentException("Book with ID " + bookId + " is not available for loan.");
	        }

	        // Decrementa a disponibilidade do livro e salva
	        book.decrementAvailable();
	        bookRepository.save(book);  // Atualiza a disponibilidade no banco de dados

	        // Cria o LoanItem e associa ao Loan
	        LoanItem loanItem = new LoanItem(book, loan, LocalDate.now().plusDays(60));  // Define a data esperada de devolução
	        loan.getItems().add(loanItem);  // Associa o LoanItem ao Loan
	    }

	    // Salva o Loan (os LoanItems serão salvos automaticamente em cascata)
	    loanRepository.save(loan);

	    return loan;  // Retorna o empréstimo criado
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
     * @param obj    O objeto de empréstimo contendo os dados atualizados
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
     * Atualiza os dados de um empréstimo existente com base nos dados fornecidos.
     *
     * @param entity O empréstimo existente
     * @param obj    O empréstimo com os dados atualizados
     */
	private void updateData(Loan entity, Loan obj) {
		entity.setLoan(obj.getLoan());
		entity.setDevolution(obj.getDevolution());
		entity.setUser(obj.getUser());
		
		entity.setLoanStatus(obj.getLoanStatus());
	}
	
	public long countLoans() {
        return loanRepository.count();
    }
}