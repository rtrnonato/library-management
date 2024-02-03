package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.rtrnonato.library_management.entities.enums.LoanStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_loan")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant loan;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant devolution;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(name = "loan_book", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> book = new HashSet<>();
    
	private int loanStatus;

	public Loan() {

	}

	public Loan(Integer id, Instant loan, Instant devolution, User user, LoanStatus loanStatus, Set<Book> book) {
		this.id = id;
		this.loan = loan;
		this.devolution = devolution;
		this.user = user;
		this.book.addAll(book);
		setLoanStatus(loanStatus);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instant getLoan() {
		return loan;
	}

	public void setLoan(Instant loan) {
		this.loan = loan;
	}

	public Instant getDevolution() {
		return devolution;
	}

	public void setDevolution(Instant devolution) {
		this.devolution = devolution;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Set<Book> getBook() {
		return book;
	}

	public void setBook(Set<Book> book) {
		this.book = book;
	}

	public LoanStatus getLoanStatus() {
		return LoanStatus.valueOf(loanStatus);
	}

	public void setLoanStatus(LoanStatus loanStatus) {
	  if (loanStatus != null) {
		this.loanStatus = loanStatus.getCode();
	  }
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
		Loan other = (Loan) obj;
		return Objects.equals(id, other.id);
	}
}
