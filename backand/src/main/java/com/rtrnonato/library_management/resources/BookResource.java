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
import com.rtrnonato.library_management.entities.Book;
import com.rtrnonato.library_management.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para operações relacionadas aos livros.
 */
@RestController
@RequestMapping(value = "/books")
@Tag(name = "Books", description = "API para operações relacionadas aos livros")
public class BookResource {
	
	@Autowired
	private BookService service;
	
	 /**
     * Retorna todos os livros.
     *
     * @return ResponseEntity contendo a lista de livros.
     */
	@GetMapping
	@Operation(summary = "Retorna todos os livros", description = "Lista todos os livros cadastrados na biblioteca")
	public ResponseEntity<List<Book>> findAll(){
		List<Book> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	/**
     * Retorna um livro pelo ID.
     *
     * @param id ID do livro a ser retornado.
     * @return ResponseEntity contendo o livro encontrado.
     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "Retorna um livro pelo ID", description = "Busca um livro específico pelo seu ID")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
		Book obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
    }
    
    /**
     * Insere um novo livro.
     *
     * @param obj Novo livro a ser inserido.
     * @return ResponseEntity contendo o livro inserido, ou HTTP 500 em caso de erro interno do servidor.
     */
    @PostMapping
    @Operation(summary = "Insere um novo livro", description = "Adiciona um novo livro ao sistema")
    public ResponseEntity<Book> insert(@RequestBody Book obj) {
    	try {
            obj = service.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Deleta um livro pelo ID.
     *
     * @param id ID do livro a ser deletado.
     * @return ResponseEntity com status HTTP 204 (No Content) se deletado com sucesso, ou HTTP 500 em caso de erro interno do servidor.
     */
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta um livro pelo ID", description = "Remove um livro do sistema pelo seu ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Atualiza um livro pelo ID.
     *
     * @param id  ID do livro a ser atualizado.
     * @param obj Novos dados do livro.
     * @return ResponseEntity contendo o livro atualizado, ou HTTP 404 se não for encontrado, ou HTTP 500 em caso de erro interno do servidor.
     */
    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualiza um livro pelo ID", description = "Modifica os dados de um livro existente")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book obj) {
    	try {
            obj = service.update(id, obj);
            if (obj == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> countBooks() {
        long count = service.countBooks();
        return ResponseEntity.ok().body(count);
    }
}