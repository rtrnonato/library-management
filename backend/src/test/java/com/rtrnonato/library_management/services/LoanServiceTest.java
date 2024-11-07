package com.rtrnonato.library_management.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.requests.UpdateLoanRequest;

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
     * Testa o método findById para garantir que um empréstimo seja encontrado pelo ID.
     */
    @Test
    void testFindById() {
        Loan existingLoan = new Loan();
        when(loanRepository.findById(1L)).thenReturn(Optional.of(existingLoan));
        Loan result = loanService.findById(1L);
        assertNotNull(result);

        // Testa exceção quando o empréstimo não existe
        when(loanRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> loanService.findById(2L));
    }

    /**
     * Testa o método createLoan para garantir que um empréstimo seja criado corretamente.
     */
    @Test
    void testCreateLoan() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Book book = new Book();
        book.setAvailable(1);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        List<Long> bookIds = Collections.singletonList(1L);
        Long userId = 1L;

        Loan loan = loanService.createLoan(bookIds, userId);

        assertNotNull(loan);
        assertEquals(LoanStatus.BORROWED, loan.getLoanStatus());
        verify(bookRepository, times(1)).save(any(Book.class));
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

        User existingUser = new User();
        existingUser.setId(1L);

        UpdateLoanRequest loanData = new UpdateLoanRequest();
        loanData.setUserId(1L);
        loanData.setLoan(LocalDate.now());
        loanData.setDevolution(LocalDate.now().plusDays(20));
        loanData.setLoanStatus("DELIVERED");

        when(loanRepository.getReferenceById(1L)).thenReturn(existingLoan);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(loanRepository.save(existingLoan)).thenReturn(existingLoan);

        Loan result = loanService.updateLoan(1L, loanData);

        assertNotNull(result);
        assertEquals(LoanStatus.DELIVERED, result.getLoanStatus());
        assertEquals(existingUser, result.getUser());
    }

    /**
     * Testa o método countLoans para garantir que ele retorne o número total de empréstimos corretamente.
     */
    @Test
    void testCountLoans() {
        long expectedCount = 10L;
        when(loanRepository.count()).thenReturn(expectedCount);

        long result = loanService.countLoans();

        assertEquals(expectedCount, result);
    }
}