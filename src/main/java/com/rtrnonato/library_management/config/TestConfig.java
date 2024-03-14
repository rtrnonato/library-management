package com.rtrnonato.library_management.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import com.rtrnonato.library_management.services.LoanService;

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
	
	@Autowired
	private LoanService loanService;

	@Override
	public void run(String... args) throws Exception {
		
		Book b1 = new Book(null,"A","a","a",LocalDate.of(2023, Month.JANUARY, 6),563723,20,10);
		Book b2 = new Book(null,"B","b","b",LocalDate.of(2012, Month.JANUARY, 7),3453,10,40);
		User u1 = new User(null,"Ana","ana@gmail.com");
		
		bookRepository.saveAll(Arrays.asList(b1,b2));
		userRepository.saveAll(Arrays.asList(u1));
		
		List<Long> bookIds = Arrays.asList(b1.getId(), b2.getId());
		Loan loan = loanService.createLoan(bookIds, u1.getId());
		
		if (loan != null) {
            System.out.println("Empréstimo criado com sucesso: " + loan);
        } else {
            System.out.println("Falha ao criar o empréstimo");
        }
		
	}

}
