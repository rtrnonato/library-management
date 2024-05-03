package com.rtrnonato.library_management.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa um usuário no sistema.
 */
@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//O nome do usuário.
	private String name;
	
	//O email do usuário.
	private String email;
	
	//Lista de empréstimos associados ao usuário
	@OneToMany(mappedBy = "user")
	List<Loan> loan = new ArrayList<>();

	/**
     * Construtor padrão.
     */
	public User() {
	}
	
	/**
     * Construtor com argumentos.
     * 
     * @param id    O identificador do usuário.
     * @param name  O nome do usuário.
     * @param email O email do usuário.
     * @throws IllegalArgumentException Se o nome ou o email forem nulos ou vazios.
     */
	public User(Long id, String name, String email) {
		if (name == null || name.trim().isEmpty()) {
	        throw new IllegalArgumentException("name cannot be null or empty");
	    }
	    if (email == null || email.trim().isEmpty()) {
	        throw new IllegalArgumentException("email cannot be null or empty");
	    }
	    
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Loan> getLoan() {
		return loan;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}