package com.rtrnonato.library_management.services.exceptions;

/**
 * Exceção personalizada para erros relacionados ao banco de dados.
 */
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	 /**
     * Construtor que cria uma exceção com uma mensagem de erro.
     * 
     * @param message A mensagem de erro detalhando a exceção.
     */
	public DatabaseException(String message) {
		super(message);
	}

	/**
     * Construtor que cria uma exceção com uma mensagem de erro e uma causa raiz.
     * 
     * @param message A mensagem de erro detalhando a exceção.
     * @param cause A causa raiz da exceção, normalmente a exceção original que ocorreu.
     */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}