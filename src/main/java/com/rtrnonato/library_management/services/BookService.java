package com.rtrnonato.library_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de serviço para operações relacionadas a livros.
 */
@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	/**
     * Retorna uma lista de todos os livros.
     * 
     * @return Lista de livros
     */
	public List<Book> findAll() {
		return repository.findAll();
	}

	/**
     * Retorna um livro pelo ID.
     * 
     * @param id ID do livro
     * @return O livro encontrado
     * @throws ResourceNotFoundException se o livro não for encontrado
     */
	public Book findById(Long id) {
		Optional<Book> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
	}
	
	/**
     * Insere um novo livro.
     * 
     * @param obj Livro a ser inserido
     * @return O livro inserido
     */
	public Book insert(Book obj) {
		return repository.save(obj);
	}
	
    /**
     * Exclui um livro pelo ID.
     * 
     * @param id ID do livro a ser excluído
     * @throws ResourceNotFoundException se o livro não for encontrado
     */
	public void delete(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException("Book not found with ID: " + id);
			}
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Book not found with ID: " + id);
		}
	}

	/**
     * Atualiza um livro.
     * 
     * @param id  ID do livro a ser atualizado
     * @param obj Livro com os novos dados
     * @return O livro atualizado
     * @throws ResourceNotFoundException se o livro não for encontrado
     */
	public Book update(Long id, Book obj) {
		try {
			Book entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
	}

	// Atualiza os dados de um livro
	private void updateData(Book entity, Book obj) {
		entity.setTitle(obj.getTitle());
		entity.setAuthor(obj.getAuthor());
		entity.setGender(obj.getGender());
		entity.setPublication(obj.getPublication());
		if (obj.getISBN() != null) {
	        entity.setISBN(obj.getISBN());
	    }
		if (obj.getTotal() != null) {
	        entity.setTotal(obj.getTotal());
	    }
		entity.setAvailable(obj.getAvailable());
	}
}