package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rtrnonato.library_management.entities.pk.LoanItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Representa um item associado a um empréstimo no sistema de gerenciamento de biblioteca.
 */
@Entity
@Table(name = "tb_loan_item")
public class LoanItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Identificador composto deste item de empréstimo.
	@EmbeddedId
	private LoanItemPK id = new LoanItemPK();
	
	// Empréstimo associado a este item de empréstimo.
	@ManyToOne
	@MapsId("loan")
	@JoinColumn(name = "loan_id")
	private Loan loan;
	
	// Livro associado a este item de empréstimo.
	@ManyToOne
	@MapsId("book")
	@JoinColumn(name = "book_id")
	private Book book;
	
	// Data de retorno esperada do livro associado a este item.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate expectedReturn;
	
	/**
     * Construtor padrão.
     */
	public LoanItem() {
	}
	
	/**
     * Construtor com argumentos.
     * 
     * @param book O livro associado a este item de empréstimo.
     * @param loan O empréstimo associado a este item de empréstimo.
     * @param expectedReturn A data de retorno esperada do livro.
     * @throws IllegalArgumentException Se algum dos parâmetros for nulo.
     */
	public LoanItem(Book book, Loan loan, LocalDate expectedReturn) {
		if (book == null) {
	        throw new IllegalArgumentException("Book cannot be null");
	    }
	    if (loan == null) {
	        throw new IllegalArgumentException("Loan cannot be null");
	    }
	    
		this.id.setBook(book);
		this.id.setLoan(loan);
		this.expectedReturn = expectedReturn;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Loan getLoan() {
		return id.getLoan();
	}
	 
	public void setLoan(Loan loan) {
		id.setLoan(loan);
	}
	
	public LoanItemPK getId() {
		return id;
	}

	public void setId(LoanItemPK id) {
		this.id = id;
	}

	public LocalDate getExpectedReturn() {
		return expectedReturn;
	}

	public void setExpectedReturn(LocalDate expectedReturn) {
		this.expectedReturn = expectedReturn;
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