package com.rtrnonato.library_management.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.LoanItemRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

/**
 * Classe de teste para LoanService.
 */
public class LoanServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	@Mock
	private LoanRepository loanRepository;

	@Mock
	private LoanItemRepository loanItemRepository;

	@InjectMocks
	private LoanService loanService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	/**
     * Testa o método findAll para garantir que uma lista de empréstimos seja retornada corretamente.
     */
	@Test
    void testFindAll() {
        List<Loan> loans = Collections.singletonList(new Loan());
        when(loanRepository.findAll()).thenReturn(loans);
        List<Loan> result = loanService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
	
	/**
     * Testa o método findById para garantir que um empréstimo seja encontrado pelo ID, e uma exceção seja lançada se o empréstimo não existir.
     */
	@Test
	void testFindById() {
        Loan existingLoan = new Loan();
        when(loanRepository.findById(1L)).thenReturn(Optional.of(existingLoan));
        Loan result = loanService.findById(1L);
        assertNotNull(result);
        when(loanRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> loanService.findById(2L));
	}
	
	/**
     * Testa o método createLoan para garantir que um empréstimo seja criado corretamente.
     */
	@Test
	void testCreateLoan() {
		User user = new User();
		when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
		Book book = new Book();
		book.setAvailable(1);
		when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		List<Long> bookIds = Collections.singletonList(1L);
		Long userId = 1L;
		Loan loan = loanService.createLoan(bookIds, userId);
		assertNotNull(loan);
		assertEquals(LoanStatus.BORROWED, loan.getLoanStatus());
	}

	/**
     * Testa o método returnBooks para garantir que os livros sejam devolvidos corretamente.
     */
	@Test
	void testReturnBooks() {
		Loan loan = new Loan();
		loan.setLoanStatus(LoanStatus.BORROWED);
		when(loanRepository.findById(anyLong())).thenReturn(java.util.Optional.of(loan));
		List<Long> loanIds = Collections.singletonList(1L);
		loanService.returnBooks(loanIds);
		assertEquals(LoanStatus.DELIVERED, loan.getLoanStatus());
	}

	/**
     * Testa o método deleteLoan para garantir que um empréstimo seja excluído corretamente.
     */
	@Test
	void testDeleteLoan() {
		when(loanRepository.existsById(anyLong())).thenReturn(true);
		List<Long> loanIds = Collections.singletonList(1L);
		loanService.deleteLoan(loanIds);
		verify(loanRepository, times(loanIds.size())).deleteById(anyLong());
	}
	
	/**
     * Testa o método updateLoan para garantir que um empréstimo seja atualizado corretamente.
     */
	@Test
	void testUpdateLoan() {
        Loan existingLoan = new Loan();
        existingLoan.setId(1L);
        existingLoan.setLoanStatus(LoanStatus.BORROWED);
        when(loanRepository.getReferenceById(1L)).thenReturn(existingLoan);
        when(loanRepository.save(existingLoan)).thenReturn(existingLoan);
        Loan updatedLoan = new Loan();
        updatedLoan.setId(1L);
        updatedLoan.setLoanStatus(LoanStatus.DELIVERED);
        Loan result = loanService.updateLoan(1L, updatedLoan);
        assertNotNull(result);
        assertEquals(LoanStatus.DELIVERED, result.getLoanStatus());
        when(loanRepository.getReferenceById(2L)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> loanService.updateLoan(2L, new Loan()));
	}
}