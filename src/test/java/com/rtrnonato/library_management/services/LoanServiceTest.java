package com.rtrnonato.library_management.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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

	@Test
	void testCreateLoan() {
		// Mocking user and book repositories
		User user = new User();
		when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));

		Book book = new Book();
		book.setAvailable(1);
		when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.of(book));

		// Mocking save methods
		when(bookRepository.save(any(Book.class))).thenReturn(book);

		// Test data
		List<Long> bookIds = Collections.singletonList(1L);
		Long userId = 1L;

		// Perform the method call
		Loan loan = loanService.createLoan(bookIds, userId);

		// Verify the result
		assertNotNull(loan);
		assertEquals(LoanStatus.BORROWED, loan.getLoanStatus());
		// Add more assertions as needed
	}

	@Test
	void testReturnBooks() {
		// Mocking loan repository
		Loan loan = new Loan();
		loan.setLoanStatus(LoanStatus.BORROWED);
		when(loanRepository.findById(anyLong())).thenReturn(java.util.Optional.of(loan));

		// Test data
		List<Long> loanIds = Collections.singletonList(1L);

		// Perform the method call
		loanService.returnBooks(loanIds);

		// Verify the behavior
		assertEquals(LoanStatus.DELIVERED, loan.getLoanStatus());
		// Add more assertions as needed
	}

	@Test
	void testDeleteLoan() {
		// Mocking loan repository
		when(loanRepository.existsById(anyLong())).thenReturn(true);

		// Test data
		List<Long> loanIds = Collections.singletonList(1L);

		// Perform the method call
		loanService.deleteLoan(loanIds);

		// Verify the behavior
		verify(loanRepository, times(loanIds.size())).deleteById(anyLong());
		// Add more assertions as needed
	}
}
