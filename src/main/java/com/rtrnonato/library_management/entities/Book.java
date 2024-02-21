package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_book")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String title;
	private String author;
	private String gender;
	private LocalDate publication;
	private Integer ISBN;
	private Integer total;
	private Integer available; 
	
	@ManyToMany
	private Set<Loan> loan = new HashSet<>();
	
	@OneToMany(mappedBy = "id.book")
	private Set<LoanItem> items = new HashSet<>();

	public Book() {
		
	}
	
	public Book(Long id, String title, String author, String gender, LocalDate publication, Integer iSBN, Integer total, Integer available) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.gender = gender;
		this.publication = publication;
		ISBN = iSBN;
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
		ISBN = iSBN;
	}

	public Integer getTootal() {
		return total;
	}

	public void setTootal(Integer total) {
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
	
	@JsonIgnore
	public Set<Loan> getLoans() {
		Set<Loan> set = new HashSet<>();
		for (LoanItem x : items) {
			set.add(x.getLoan());
		}
		return set;
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
