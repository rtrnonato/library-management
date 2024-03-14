package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rtrnonato.library_management.entities.pk.LoanItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_loan_item")
public class LoanItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private LoanItemPK id = new LoanItemPK();
	
	@ManyToOne
	@JoinColumn(name = "loan_item_id")
	private Loan loan;
	
	private Instant expectedReturn;
	private Instant actualReturn;
	
	public LoanItem() {
		
	}
	
	public LoanItem(Book book, Loan loan, Instant expectedReturn, Instant actualReturn) {
		id.setBook(book);
		id.setLoan(loan);
		this.expectedReturn = expectedReturn;
		this.actualReturn = actualReturn;
	}
	
	
	public Book getBook() {
		return id.getBook();
	}
	 
	public void setBook(Book book) {
		id.setBook(book);
	}
	
	 @JsonIgnore
	public Loan getLoan() {
		return id.getLoan();
	}
	 
	public void setLoan(Loan loan) {
		id.setLoan(loan);
	}
	
	 @JsonIgnore
	public LoanItemPK getId() {
		return id;
	}

	public void setId(LoanItemPK id) {
		this.id = id;
	}

	public Instant getExpectedReturn() {
		return expectedReturn;
	}

	public void setExpectedReturn(Instant expectedReturn) {
		this.expectedReturn = expectedReturn;
	}

	public Instant getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(Instant actualReturn) {
		this.actualReturn = actualReturn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanItem other = (LoanItem) obj;
		return Objects.equals(id, other.id);
	}
	
}
