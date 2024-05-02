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
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;
import com.rtrnonato.library_management.repositories.LoanItemRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private LoanRepository loanRepository;

	@Mock
	private LoanItemRepository loanItemRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void testFindAll() {
        List<User> Users = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(Users);
        List<User> result = userService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
	
	@Test
	void testFindById() {
        User existingUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        User result = userService.findById(1L);
        assertNotNull(result);
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.findById(2L));
    }
	
	@Test
	void testInsert() {
		User user = new User();
		when(userRepository.save(user)).thenReturn(user);
		User result = userService.insert(user);
		assertNotNull(result);
	}

	@Test
	void testDelete() {
		when(userRepository.existsById(1L)).thenReturn(true); 
		assertDoesNotThrow(() -> userService.delete(1L));
		when(userRepository.existsById(2L)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> userService.delete(2L));
	}

	@Test
	void testUpdate() {
		User existingUser = new User();
		when(userRepository.getReferenceById(1L)).thenReturn(existingUser);
		when(userRepository.save(existingUser)).thenReturn(existingUser);
		User result = userService.update(1L, existingUser);
		assertNotNull(result);
		when(userRepository.getReferenceById(2L)).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class, () -> userService.update(2L, new User()));
	}
}
