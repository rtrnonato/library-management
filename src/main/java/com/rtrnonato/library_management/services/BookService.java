package com.rtrnonato.library_management.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	public List<Book> findAll() {
		return repository.findAll();
	}

	public Book findById(Long id) {
		Optional<Book> obj = repository.findById(id);
		return obj.orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
	}

	public Book insert(Book obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public Book update(Long id, Book obj) {
		try {
			Book entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Book entity, Book obj) {
		entity.setTitle(obj.getTitle());
		entity.setAuthor(obj.getAuthor());
		entity.setGender(obj.getGender());
		entity.setPublication(obj.getPublication());
		entity.setISBN(obj.getISBN());
		entity.setTotal(obj.getTotal());
		entity.setAvailable(obj.getAvailable());
	}
}
