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
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

public class BookServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void testFindAll() {
        List<Book> books = Collections.singletonList(new Book());
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
	
	@Test
	void testFindById() {
        Book existingBook = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        Book result = bookService.findById(1L);
        assertNotNull(result);
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> bookService.findById(2L));
    }
	
	@Test
	void testInsert() {
		Book book = new Book();
		when(bookRepository.save(book)).thenReturn(book);
		Book result = bookService.insert(book);
		assertNotNull(result);
	}

	@Test
	void testDelete() {
		when(bookRepository.existsById(1L)).thenReturn(true); 
		assertDoesNotThrow(() -> bookService.delete(1L));
		when(bookRepository.existsById(2L)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> bookService.delete(2L));
	}

	@Test
	void testUpdate() {
		Book existingBook = new Book();
		when(bookRepository.getReferenceById(1L)).thenReturn(existingBook);
		when(bookRepository.save(existingBook)).thenReturn(existingBook);
		Book result = bookService.update(1L, existingBook);
		assertNotNull(result);
		when(bookRepository.getReferenceById(2L)).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class, () -> bookService.update(2L, new Book()));
	}
}
