package com.rtrnonato.library_management.resources;

import java.util.List;

public class CreateLoanRequest {

	private List<Long> bookIds;
    private Long userId;
    
    public CreateLoanRequest() {
	}
    
    public CreateLoanRequest(List<Long> bookIds, Long userId) {
		this.bookIds = bookIds;
		this.userId = userId;
	}

	public List<Long> getBookIds() {
		return bookIds;
	}

	public void setBookIds(List<Long> bookIds) {
		this.bookIds = bookIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
