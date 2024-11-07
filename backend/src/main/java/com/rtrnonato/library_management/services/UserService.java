package com.rtrnonato.library_management.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.rtrnonato.library_management.entities.User;
import com.rtrnonato.library_management.repositories.UserRepository;
import com.rtrnonato.library_management.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de serviço para operações relacionadas a usuários.
 */
@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	/**
     * Retorna uma lista de todos os usuários cadastrados.
     *
     * @return Lista de usuários
     */
	public List<User> findAll() {
		return repository.findAll();
	}

	/**
     * Busca um usuário pelo ID.
     *
     * @param id ID do usuário a ser buscado
     * @return O usuário encontrado
     * @throws ResourceNotFoundException Se o usuário não for encontrado
     */
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
	}

	/**
     * Insere um novo usuário.
     *
     * @param obj Usuário a ser inserido
     * @return O usuário inserido
     */
	public User insert(User obj) {
		return repository.save(obj);
	}

	/**
     * Deleta um usuário pelo ID.
     *
     * @param id ID do usuário a ser deletado
     * @throws ResourceNotFoundException Se o usuário não for encontrado
     */
	public void delete(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException("User not found with ID: " + id);
			}
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("User not found with ID: " + id);
		}
	}

	/**
     * Atualiza os dados de um usuário.
     *
     * @param id  ID do usuário a ser atualizado
     * @param obj Novos dados do usuário
     * @return O usuário atualizado
     * @throws ResourceNotFoundException Se o usuário não for encontrado
     */
	public User update(Long id, User obj) {
		try {
		    User entity = repository.getReferenceById(id);
		    updateData(entity, obj);
		    return repository.save(entity);
		} catch(EntityNotFoundException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("User not found with ID: " + id);
		}
	}

    /**
     * Atualiza os dados de um usuário existente com base nos novos dados fornecidos.
     *
     * @param entity Usuário existente
     * @param obj    Novos dados do usuário
     */
	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
	}
	
	public long countUsers() {
        return repository.count();
    }
}