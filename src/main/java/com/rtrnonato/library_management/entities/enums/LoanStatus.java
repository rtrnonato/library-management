package com.rtrnonato.library_management.entities.enums;

public enum LoanStatus {
	
	BORROWED(1),
	DELIVERED(2);
	
	private int code;
	
	private LoanStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static LoanStatus valueOf(int code) {
		for (LoanStatus value : LoanStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid LoanStatus code");
	}

}
