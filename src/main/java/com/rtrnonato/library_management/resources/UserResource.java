package com.rtrnonato.library_management.resources;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.services.UserService;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;

/**
 * Controlador REST para operações relacionadas aos usuários.
 */
@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	/**
     * Retorna todos os usuários.
     * 
     * @return Uma lista de todos os usuários.
     */
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	/**
     * Retorna um usuário pelo ID.
     * 
     * @param id O ID do usuário a ser encontrado.
     * @return O usuário encontrado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
    	User obj = service.findById(id);
    	return ResponseEntity.ok().body(obj);
    }
    
    /**
     * Insere um novo usuário.
     * 
     * @param obj O usuário a ser inserido.
     * @return O usuário inserido com sua URI.
     */
    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
    	obj = service.insert(obj);
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
    	return ResponseEntity.created(uri).body(obj);
    }

    /**
     * Deleta um usuário pelo ID.
     * 
     * @param id O ID do usuário a ser deletado.
     * @return Uma resposta vazia.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

    /**
     * Atualiza um usuário pelo ID.
     * 
     * @param id  O ID do usuário a ser atualizado.
     * @param obj O usuário com os dados atualizados.
     * @return O usuário atualizado.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
    	try {
            obj = service.update(id, obj);
            return ResponseEntity.ok().body(obj);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    } 
}