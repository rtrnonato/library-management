package com.rtrnonato.library_management.resources.exceptions;

import java.time.Instant;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe para tratamento de exceções globais na aplicação.
 */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	/**
     * Método para tratar a exceção ResourceNotFoundException.
     * @param e Exceção ResourceNotFoundException lançada
     * @param request HttpServletRequest para obter detalhes da requisição
     * @return ResponseEntity contendo um objeto StandardError e o status HTTP correspondente
     */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(),status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	/**
     * Método para tratar a exceção EmptyResultDataAccessException.
     * @param e Exceção EmptyResultDataAccessException lançada
     * @param request HttpServletRequest para obter detalhes da requisição
     * @return ResponseEntity contendo um objeto StandardError e o status HTTP correspondente
     */
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<StandardError> emptyResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest request) {
	    String error = "Resource not found";
	    HttpStatus status = HttpStatus.NOT_FOUND;
	    StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}
}
