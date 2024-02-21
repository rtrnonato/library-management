package com.rtrnonato.library_management.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.entities.LoanItem;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.entities.enums.LoanStatus;
import com.rtrnonato.library_management.repositories.BookRepository;
import com.rtrnonato.library_management.repositories.LoanItemRepository;
import com.rtrnonato.library_management.repositories.LoanRepository;
import com.rtrnonato.library_management.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoanItemRepository loanItemRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Book b1 = new Book(null,"A","a","a",LocalDate.of(2023, Month.JANUARY, 6),563723,20,10);
		Book b2 = new Book(null,"B","b","b",LocalDate.of(2012, Month.JANUARY, 7),3453,10,40);
		User u1 = new User(null,"Ana","ana@gmail.com");
		
		bookRepository.saveAll(Arrays.asList(b1,b2));
		
		userRepository.saveAll(Arrays.asList(u1));
		
		Set<Book> book = new HashSet<>();
		book.add(b1);
		book.add(b2);
		Loan l1 = new Loan(null, Instant.parse("2019-06-20T19:53:07Z"), Instant.parse("2023-06-20T19:53:07Z"), u1, LoanStatus.BORROWED,book);
		
		loanRepository.saveAll(Arrays.asList(l1));
		
		LoanItem li1 = new LoanItem(b1,l1,Instant.parse("2023-06-20T19:53:07Z"), l1.getDevolution());
		
	    loanItemRepository.saveAll(Arrays.asList(li1));
		
	}

}
