package com.rtrnonato.library_management.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.services.LoanService;

@RestController
@RequestMapping(value = "/loans")
public class LoanResource {
	
	@Autowired
	private LoanService service;
	
	@GetMapping
	public ResponseEntity<List<Loan>> findAll(){
		List<Loan> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
    @GetMapping(value = "/{id}")
    public ResponseEntity<Loan> findById(@PathVariable Integer id) {
    	Loan obj = service.findById(id);
    	return ResponseEntity.ok().body(obj);
    	
    }

}
