package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * Representa um emprestimo de livro.
 */
@Entity
@Table(name = "tb_loan")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Data do empréstimo
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate loan;

	// Data de devolução
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private LocalDate devolution;

	// Usuário que realizou o empréstimo
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	// Livros emprestados
	@ManyToMany
	@JoinTable(name = "loan_book", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> book = new HashSet<>();
    
	// Itens do empréstimo
	@OneToMany(mappedBy = "loan")
	private Set<LoanItem> items = new HashSet<>();
	
	// Status do empréstimo
	private int loanStatus;

	/**
     * Construtor padrão.
     */
	public Loan() {
	}

	 /**
     * Construtor com argumentos.
     *
     * @param id Identificador do empréstimo
     * @param loan Data do empréstimo
     * @param devolution Data de devolução
     * @param user Usuário que realizou o empréstimo
     * @param loanStatus Status do empréstimo
     * @param book Livros emprestados
     * @throws IllegalArgumentException Se a data do empréstimo for nula, se o usuário for nulo
     *                                  ou se nenhum livro for fornecido
     */
	public Loan(Long id, LocalDate loan, LocalDate devolution, User user, LoanStatus loanStatus, Set<Book> book) {
		// Verificações para garantir que parâmetros essenciais não sejam nulos
		if (loan == null) {
			throw new IllegalArgumentException("Loan date cannot be null");
		}
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		if (book == null || book.isEmpty()) {
            throw new IllegalArgumentException("At least one book must be provided");
        }
		
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
	
	@JsonIgnore
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