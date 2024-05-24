package com.rtrnonato.library_management.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Representa uma estrutura padronizada para informações de erro.
 */
public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Momento em que ocorreu o erro
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timestamp;
	
	// Status HTTP do erro
	private Integer status;
	
	// Tipo de erro
	private String error;
	
	// Mensagem descritiva do erro
	private String message;
	
	// Caminho do recurso onde ocorreu o erro
	private String path;
	
	/**
     * Construtor padrão.
     */
	public StandardError() {
	}

	/**
     * Construtor com argumentos.
     *
     * @param timestamp O momento em que ocorreu o erro
     * @param status O status HTTP do erro
     * @param error O tipo de erro
     * @param message A mensagem descritiva do erro
     * @param path O caminho do recurso onde ocorreu o erro
     */
	public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}