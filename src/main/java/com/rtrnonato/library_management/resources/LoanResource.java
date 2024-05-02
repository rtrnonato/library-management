package com.rtrnonato.library_management.resources;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.rtrnonato.library_management.entities.Loan;
import com.rtrnonato.library_management.services.LoanService;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

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
    public ResponseEntity<Loan> findById(@PathVariable Long id) {
    	Loan obj = service.findById(id);
    	return ResponseEntity.ok().body(obj);  	
    }
    
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanRequest request) {
		List<Long> bookIds = request.getBookIds();
		Long userId = request.getUserId();
        Loan loan = service.createLoan(bookIds, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loan.getId()).toUri();
        return ResponseEntity.created(uri).body(loan);
    }
    
    @PostMapping(value = "/return")
    public ResponseEntity<Void> returnBooks(@RequestBody List<Long> loanIds) {
        service.returnBooks(loanIds);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLoan(@RequestBody List<Long> loanIds) {
        try {
            service.deleteLoan(loanIds);
            return ResponseEntity.ok("Loans deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or more loans not found.");
        }
    }

    @PutMapping("/update/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long loanId, @RequestBody Loan loan) {
        try {
            Loan updatedLoan = service.updateLoan(loanId, loan);
            return ResponseEntity.ok(updatedLoan);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
