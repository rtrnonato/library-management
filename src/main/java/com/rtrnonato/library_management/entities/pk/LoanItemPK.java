package com.rtrnonato.library_management.entities.pk;

import java.io.Serializable;
import java.util.Objects;
import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class LoanItemPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name = "loan_id")
	private Loan loan;
	
	@ManyToOne
    @JoinColumn(name = "book_id")
	private Book book;
	
    
	public Loan getLoan() {
		return loan;
	}
    
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	 
	public Book getBook() {
		return book;
	}
	 
	public void setBook(Book book) {
		this.book = book;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(book, loan);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanItemPK other = (LoanItemPK) obj;
		return Objects.equals(book, other.book) && Objects.equals(loan, other.loan);
	}
	
}
