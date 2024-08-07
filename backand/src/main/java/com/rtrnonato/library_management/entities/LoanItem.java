package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rtrnonato.library_management.entities.pk.LoanItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@JoinColumn(name = "loan_item_id")
	private Loan loan;
	
	// Livro associado a este item de empréstimo.
	@ManyToOne
	@JoinColumn(name = "book_item_id")
	private Book bookItem;
	
	// Conjunto de itens de empréstimo associados a este item.
	@OneToMany(fetch = FetchType.EAGER)
    private Set<LoanItem> items = new HashSet<>();
	
	// Data de retorno esperada do livro associado a este item.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate expectedReturn;
	
	// Data de retorno real do livro associado a este item.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate actualReturn;
	
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
     * @param actualReturn A data de retorno real do livro.
     * @throws IllegalArgumentException Se algum dos parâmetros for nulo.
     */
	public LoanItem(Book book, Loan loan, LocalDate expectedReturn, LocalDate actualReturn) {
		if (book == null) {
	        throw new IllegalArgumentException("Book cannot be null");
	    }
	    if (loan == null) {
	        throw new IllegalArgumentException("Loan cannot be null");
	    }
	    
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

	public LocalDate getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(LocalDate actualReturn) {
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