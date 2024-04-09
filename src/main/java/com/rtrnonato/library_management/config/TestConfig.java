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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		/*Book b1 = new Book(null,"A","a","a",LocalDate.of(2023, Month.JANUARY, 6),563723,20,10);
		Book b2 = new Book(null,"B","b","b",LocalDate.of(2012, Month.JANUARY, 7),3453,10,40);
		User u1 = new User(null,"Ana","ana@gmail.com");
		
		Book b3 = new Book(null,"e","e","e",LocalDate.of(2023, Month.JANUARY, 6),1231,67,12);
		Book b4 = new Book(null,"z","z","z",LocalDate.of(2013, Month.AUGUST,10),3234234,12,23);
		User u2 = new User(null,"Maria","maria@gmail.com");
		
		bookRepository.saveAll(Arrays.asList(b1,b2));
		userRepository.saveAll(Arrays.asList(u1));
		
		List<Long> bookIds = Arrays.asList(b1.getId(), b2.getId());
		Loan loan = loanService.createLoan(bookIds, u1.getId());
		
		if (loan != null) {
            System.out.println("Empréstimo criado1 com sucesso: " + loan);
        } else {
            System.out.println("Falha ao criar o empréstimo");
        }
		
		loanRepository.save(loan);
		
		List<Long> bookIds2 = Arrays.asList(b3.getId(), b4.getId());
		Loan loan2 = loanService.createLoan(bookIds2, u2.getId());
		
		loanRepository.save(loan2);
		
		if (loan2 != null) {
            System.out.println("Empréstimo criado2 com sucesso: " + loan2);
        } else {
            System.out.println("Falha ao criar o empréstimo");
        }
		
		Loan updateLoan = loanService.updateLoan(loan.getId(), loan2);
		
		loanRepository.save(updateLoan);
		
		if (updateLoan != null) {
            System.out.println("Empréstimo atualizado com sucesso: " + updateLoan);
        } else {
            System.out.println("Falha ao atualizar o empréstimo");
        }
		
		List<Long> loanIds = Arrays.asList(updateLoan.getId());
		
		loanService.deleteLoan(loanIds);
		
		loanRepository.save(updateLoan);
		
		if (updateLoan == null) {
            System.out.println("Empréstimo deletado com sucesso: " + updateLoan);
        } else {
            System.out.println("Falha ao deletar o empréstimo");
        }
		
		/*List<Long> loanIds = Arrays.asList(loan.getId());
		loanService.returnBooks(loanIds);
		
		entityManager.flush();
	    entityManager.clear();
		
		System.out.println(loan.getLoanStatus());
		if (loan.getLoanStatus() == LoanStatus.DELIVERED) {
			System.out.println("Livros devolvidos com sucesso: " + loan);
		} else {
            System.out.println("Falha ao devolver os livros");
        }*/
	}
}
