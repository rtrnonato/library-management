package com.rtrnonato.library_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.repositories.LoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository repository;
	
	public List<Loan> findAll() {
		return repository.findAll();
	}
	
	public Loan findById(Integer id) {
		Optional<Loan> obj = repository.findById(id);
		return obj.get();
	}
}
