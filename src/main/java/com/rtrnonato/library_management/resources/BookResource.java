package com.rtrnonato.library_management.resources;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rtrnonato.library_management.entities.Book;

@RestController
@RequestMapping(value = "/books")
public class BookResource {
	
	@GetMapping
	public ResponseEntity<Book> findAll(){
		Book b = new Book(1,"Artur","artur","terror",LocalDate.of(2022, Month.JANUARY, 1),123123,200,150);
		return ResponseEntity.ok().body(b);
	}

}
