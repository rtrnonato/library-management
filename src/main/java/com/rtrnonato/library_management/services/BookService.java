package com.rtrnonato.library_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;
	
	public List<Book> findAll() {
		return repository.findAll();
	}
	
	public Book findById(Integer id) {
		Optional<Book> obj = repository.findById(id);
		return obj.get();
	}
}
