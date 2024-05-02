package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tb_loan")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate loan;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate devolution;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(name = "loan_book", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> book = new HashSet<>();
    
	@OneToMany(mappedBy = "loan")
	@Fetch(FetchMode.JOIN)
	private Set<LoanItem> items = new HashSet<>();
	
	private int loanStatus;

	public Loan() {

	}

	public Loan(Long id, LocalDate loan, LocalDate devolution, User user, LoanStatus loanStatus, Set<Book> book) {
		this.id = id;
		this.loan = loan;
		this.devolution = devolution;
		this.user = user;
		this.book = book;
		setLoanStatus(loanStatus);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		this.loanStatus = loanStatus.getCode();
	}
	
	public Set<LoanItem> getItems() {
		return items;
	}
	
	public void setItems(Set<LoanItem> items) {
		this.items = items;
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
