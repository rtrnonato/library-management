package com.rtrnonato.library_management.services.exceptions;

/**
 * Exceção que indica que um recurso específico não pôde ser encontrado.
 */
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
     * Construtor que cria uma nova instância de ResourceNotFoundException com uma mensagem de erro padrão.
     * @param id O identificador do recurso que não pôde ser encontrado.
     */
	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}