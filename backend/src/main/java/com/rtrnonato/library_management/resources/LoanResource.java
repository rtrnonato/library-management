package com.rtrnonato.library_management.resources;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
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
import com.rtrnonato.library_management.requests.CreateLoanRequest;
import com.rtrnonato.library_management.requests.UpdateLoanRequest;
import com.rtrnonato.library_management.services.LoanService;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * Controlador REST para operações relacionadas aos empréstimos.
 */
@RestController
@RequestMapping(value = "/loans")
@Tag(name = "Loans", description = "Operações relacionadas aos empréstimos")
public class LoanResource {
	
	@Autowired
	private LoanService service;
	
	/**
	 * Retorna todos os empréstimos.
	 *
	 * @return ResponseEntity contendo a lista de empréstimos.
	 */
	@GetMapping
	@Operation(summary = "Retorna todos os empréstimos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de empréstimos encontrada", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Loan.class))))
		})
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
    @Operation(summary = "Retorna um empréstimo pelo ID")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Empréstimo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class))),
    		@ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    	})
    public ResponseEntity<Loan> findById(@PathVariable Long id) {
		try {
			Loan obj = service.findById(id);
			return ResponseEntity.ok().body(obj);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
    
    /**
     * Cria um novo empréstimo.
     *
     * @param request Objeto contendo IDs dos livros e ID do usuário para o novo empréstimo.
     * @return ResponseEntity contendo o novo empréstimo criado.
     */
    @PostMapping
    @Operation(summary = "Cria um novo empréstimo", description = "Adiciona um novo empréstimo ao sistema")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class))),
    		@ApiResponse(responseCode = "400", description = "Requisição inválida")
    	})
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
    @Operation(summary = "Retorna os livros de um ou mais empréstimos", description = "Marca os livros como retornados")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Livros retornados com sucesso"),
    		@ApiResponse(responseCode = "404", description = "Empréstimos não encontrados")
    	})
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
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta um ou mais empréstimos", description = "Remove os empréstimos do sistema")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Empréstimos deletados com sucesso"),
    		@ApiResponse(responseCode = "404", description = "Um ou mais empréstimos não encontrados")
    	})
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
     * @param loanData Novos dados do empréstimo.
     * @return ResponseEntity contendo o empréstimo atualizado, ou HTTP 404 se o empréstimo não foi encontrado.
     */
    @PutMapping("/update/{loanId}")
    @Operation(summary = "Atualiza um empréstimo", description = "Modifica os dados de um empréstimo existente")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class))),
    		@ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    	})
    public ResponseEntity<Loan> updateLoan(@PathVariable Long loanId, @RequestBody UpdateLoanRequest loanData) {
        try {
            Loan updatedLoan = service.updateLoan(loanId, loanData);
            return ResponseEntity.ok(updatedLoan);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    /**
     * Retorna a contagem total de empréstimos.
     *
     * @return ResponseEntity contendo a contagem total de livros.
     */
    @GetMapping("/count")
	@Operation(summary = "Contagem de empréstimos", description = "Retorna o número total de empréstimos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
	})
    public ResponseEntity<Long> countLoans() {
        long count = service.countLoans();
        return ResponseEntity.ok().body(count);
    }
}