package com.rtrnonato.library_management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrnonato.library_management.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
}
