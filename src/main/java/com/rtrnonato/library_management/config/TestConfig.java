package com.rtrnonato.library_management.config;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.repositories.BookRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Book b1 = new Book(null,"A","a","a",LocalDate.of(2023, Month.JANUARY, 6),563723,20,10);
		
		bookRepository.saveAll(Arrays.asList(b1));
	}

}
