package com.rtrnonato.library_management.resources.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe de teste para ResourceExceptionHandler.
 */
public class ResourceExceptionHandlerTest {
	
	/**
     * Testa o tratamento de exceção para ResourceNotFoundException, verificando se o status retornado é NOT_FOUND.
     */
	@Test
	public void resourceNotFound_ShouldReturnNotFoundStatus() {
		// Arrange
		ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
		HttpServletRequest request = mock(HttpServletRequest.class);
		ResourceExceptionHandler handler = new ResourceExceptionHandler();

		// Act
		ResponseEntity<StandardError> response = handler.resourceNotFound(exception, request);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
     * Testa o tratamento de exceção para ResourceNotFoundException, verificando se o objeto StandardError retornado contém a mensagem e o status corretos.
     */
	@Test
	public void resourceNotFound_ShouldReturnStandardError() {
		// Arrange
		String errorMessage = "Resource not found";
		ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
		HttpServletRequest request = mock(HttpServletRequest.class);
		ResourceExceptionHandler handler = new ResourceExceptionHandler();

		// Act
		ResponseEntity<StandardError> response = handler.resourceNotFound(exception, request);

		// Assert
		StandardError error = response.getBody();
		assertEquals(errorMessage, error.getMessage());
		assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
	}

	/**
     * Testa o tratamento de exceção para EmptyResultDataAccessException, verificando se o status retornado é NOT_FOUND.
     */
	@Test
	public void emptyResultDataAccessException_ShouldReturnNotFoundStatus() {
		// Arrange
		EmptyResultDataAccessException exception = new EmptyResultDataAccessException(1);
		HttpServletRequest request = mock(HttpServletRequest.class);
		ResourceExceptionHandler handler = new ResourceExceptionHandler();

		// Act
		ResponseEntity<StandardError> response = handler.emptyResultDataAccessException(exception, request);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
     * Testa o tratamento de exceção para EmptyResultDataAccessException, verificando se o objeto StandardError retornado contém a mensagem e o status corretos.
     */
	@Test
	public void emptyResultDataAccessException_ShouldReturnStandardError() {
		// Arrange
		String errorMessage = "Resource not found";
		EmptyResultDataAccessException exception = new EmptyResultDataAccessException(errorMessage, 1);
		HttpServletRequest request = mock(HttpServletRequest.class);
		ResourceExceptionHandler handler = new ResourceExceptionHandler();

		// Act
		ResponseEntity<StandardError> response = handler.emptyResultDataAccessException(exception, request);

		// Assert
		StandardError error = response.getBody();
		assertEquals(errorMessage, error.getMessage());
		assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
	}
}