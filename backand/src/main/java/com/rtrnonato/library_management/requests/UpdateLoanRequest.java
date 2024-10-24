package com.rtrnonato.library_management.requests;

import java.time.LocalDate;

public class UpdateLoanRequest {

	private LocalDate loan;
    private LocalDate devolution;
    private String loanStatus;
    private Long userId;
    
    public  UpdateLoanRequest() {
	}

    
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
