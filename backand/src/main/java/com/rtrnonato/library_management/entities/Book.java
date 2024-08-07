package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa um livro na biblioteca.
 */
@Entity
@Table(name = "tb_book")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Título do livro
    private String title;

    // Autor do livro
    private String author;

    // Gênero do livro
    private String gender;

    // Data de publicação do livro
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate publication;

    // Número ISBN do livro
    private Integer ISBN;

    // Total de exemplares do livro
    private Integer total;

    // Número de exemplares disponíveis do livro
    private Integer available;
    
    // Conjunto de empréstimos associados ao livro
	@ManyToMany
	private Set<Loan> loan = new HashSet<>();
	
	// Conjunto de itens de empréstimo associados ao livro
	@OneToMany(mappedBy = "bookItem")
	@Fetch(FetchMode.JOIN)
	private Set<LoanItem> items = new HashSet<>();

	/**
     * Construtor padrão.
     */
	public Book() {
	}
	
	/**
     * Construtor com argumentos.
     * 
     * @param id Identificador do livro
     * @param title Título do livro
     * @param author Autor do livro
     * @param gender Gênero do livro
     * @param publication Data de publicação do livro
     * @param ISBN Número ISBN do livro
     * @param total Total de exemplares do livro
     * @param available Número de exemplares disponíveis do livro
     */
	public Book(Long id, String title, String author, String gender, LocalDate publication, Integer iSBN, Integer total, Integer available) {
		// Verificações para garantir que parâmetros essenciais não sejam nulos
		if (title == null || title.trim().isEmpty()) {
	        throw new IllegalArgumentException("Title cannot be null or empty");
	    }
	    if (author == null || author.trim().isEmpty()) {
	        throw new IllegalArgumentException("Author cannot be null or empty");
	    }
	    
		this.id = id;
		this.title = title;
		this.author = author;
		this.gender = gender;
		this.publication = publication;
		this.ISBN = iSBN;
		this.total = total;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getPublication() {
		return publication;
	}

	public void setPublication(LocalDate publication) {
		this.publication = publication;
	}

	public Integer getISBN() {
		return ISBN;
	}

	public void setISBN(Integer iSBN) {
		if (iSBN == null) {
	        throw new IllegalArgumentException("ISBN cannot be null");
		}
		if (ISBN < 0) {
	        throw new IllegalArgumentException("ISBN cannot be negative");
	    }
		this.ISBN = iSBN;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		if (total < 0) {
	        throw new IllegalArgumentException("Total cannot be negative");
	    }
		this.total = total;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Set<Loan> getLoan() {
		return loan;
	}

	public void setLoan(Set<Loan> loan) {
		this.loan = loan;
	}
	
	/**
     * Obtém o conjunto de empréstimos associados ao livro.
     * 
     * @return Conjunto de empréstimos
     */
	public Set<Loan> getLoans() {
		Set<Loan> set = new HashSet<>();
		for (LoanItem x : items) {
			set.add(x.getLoan());
		}
		return set;
	}
	
	/**
     * Decrementa o número de exemplares disponíveis do livro.
     * Lança uma exceção se o número disponível já for zero.
     */
	public void decrementAvailable() {
		if (this.available > 0) {
		this.available--;
		} else {
			throw new IllegalStateException("Cannot decrement available books below zero");
		}
	}
	
	/**
     * Incrementa o número de exemplares disponíveis do livro.
     */
	public void incrementAvailable() {
		this.available++;
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
		Book other = (Book) obj;
		return Objects.equals(id, other.id);
	}
}