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

/**
 * Controlador REST para operações relacionadas aos empréstimos.
 */
@RestController
@RequestMapping(value = "/loans")
public class LoanResource {
	
	@Autowired
	private LoanService service;
	
	/**
	 * Retorna todos os empréstimos.
	 *
	 * @return ResponseEntity contendo a lista de empréstimos.
	 */
	@GetMapping
	public ResponseEntity<List<Loan>> findAll(){
		List<Loan> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	/**
     * Retorna um empréstimo pelo ID.
     *
     * @param id ID do empréstimo a ser retornado.
     * @return ResponseEntity contendo o empréstimo encontrado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Loan> findById(@PathVariable Long id) {
    	Loan obj = service.findById(id);
    	return ResponseEntity.ok().body(obj);  	
    }
    
    /**
     * Cria um novo empréstimo.
     *
     * @param request Objeto contendo IDs dos livros e ID do usuário para o novo empréstimo.
     * @return ResponseEntity contendo o novo empréstimo criado.
     */
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanRequest request) {
		List<Long> bookIds = request.getBookIds();
		Long userId = request.getUserId();
        Loan loan = service.createLoan(bookIds, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loan.getId()).toUri();
        return ResponseEntity.created(uri).body(loan);
    }
    
    /**
     * Retorna os livros de um ou mais empréstimos.
     *
     * @param loanIds Lista de IDs dos empréstimos a serem retornados.
     * @return ResponseEntity com status HTTP 200 (OK) se os livros foram retornados com sucesso.
     */
    @PostMapping(value = "/return")
    public ResponseEntity<Void> returnBooks(@RequestBody List<Long> loanIds) {
        service.returnBooks(loanIds);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Deleta um ou mais empréstimos.
     *
     * @param loanIds Lista de IDs dos empréstimos a serem deletados.
     * @return ResponseEntity com status HTTP 200 (OK) e mensagem de sucesso, ou HTTP 404 se algum empréstimo não foi encontrado.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLoan(@RequestBody List<Long> loanIds) {
        try {
            service.deleteLoan(loanIds);
            return ResponseEntity.ok("Loans deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or more loans not found.");
        }
    }

    /**
     * Atualiza um empréstimo.
     *
     * @param loanId ID do empréstimo a ser atualizado.
     * @param loan   Novos dados do empréstimo.
     * @return ResponseEntity contendo o empréstimo atualizado, ou HTTP 404 se o empréstimo não foi encontrado.
     */
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