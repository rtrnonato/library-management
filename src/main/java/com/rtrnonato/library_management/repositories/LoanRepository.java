package com.rtrnonato.library_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}
