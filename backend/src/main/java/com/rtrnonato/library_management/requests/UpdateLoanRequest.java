package com.rtrnonato.library_management.requests;

import java.time.LocalDate;

/**
 * Classe que representa a requisição para atualização de um empréstimo.
 * 
 * Esta classe contém os dados necessários para atualizar as informações de um
 * empréstimo existente, incluindo as datas de empréstimo e devolução, o status
 * do empréstimo e o ID do usuário associado.
 */
public class UpdateLoanRequest {

	private LocalDate loan; // Data do empréstimo
    private LocalDate devolution; // Data de devolução prevista
    private String loanStatus; // Status atual do empréstimo
    private Long userId; // ID do usuário associado ao empréstimo
    
    /**
     * Construtor padrão.
     */
    public  UpdateLoanRequest() {
	}
    
    /**
     * Construtor que inicializa todos os campos com os valores fornecidos.
     * 
     * @param loan Data do empréstimo.
     * @param devolution Data prevista de devolução.
     * @param loanStatus Status atual do empréstimo.
     * @param userId ID do usuário associado ao empréstimo.
     */
	public UpdateLoanRequest(LocalDate loan, LocalDate devolution, String loanStatus, Long userId) {
		super();
		this.loan = loan;
		this.devolution = devolution;
		this.loanStatus = loanStatus;
		this.userId = userId;
	}


	public LocalDate getLoan() {
		return loan;
	}


	public void setLoan(LocalDate loan) {
		this.loan = loan;
	}


	public LocalDate getDevolution() {
		return devolution;
	}


	public void setDevolution(LocalDate devolution) {
		this.devolution = devolution;
	}


	public String getLoanStatus() {
		return loanStatus;
	}


	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}   
}