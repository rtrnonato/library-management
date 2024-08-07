package com.rtrnonato.library_management.entities.enums;

/**
 * Enum que representa os possíveis estados de um empréstimo.
 */
public enum LoanStatus {
	
	// Estado para empréstimo em andamento
	BORROWED(1),
	
	// Estado para empréstimo entregue
	DELIVERED(2);
	
	// Código associado ao estado do empréstimo
	private final int code;
	
	/**
     * Construtor privado do enum.
     * @param code O código associado ao estado do empréstimo.
     */
	private LoanStatus(int code) {
		this.code = code;
	}
	
	/**
     * Obtém o código associado ao estado do empréstimo.
     * @return O código do estado do empréstimo.
     */
	public int getCode() {
		return code;
	}
	
    /**
     * Obtém o enum LoanStatus correspondente ao código fornecido.
     * @param code O código do estado do empréstimo.
     * @return O enum LoanStatus correspondente ao código.
     * @throws IllegalArgumentException Se o código fornecido for inválido.
     */
	public static LoanStatus valueOf(int code) {
		for (LoanStatus value : LoanStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid LoanStatus code" + code);
	}
}