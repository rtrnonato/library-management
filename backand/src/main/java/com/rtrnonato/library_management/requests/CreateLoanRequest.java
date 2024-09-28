package com.rtrnonato.library_management.requests;

import java.util.List;

/**
 * Classe que representa a requisição para criação de um novo empréstimo.
 * 
 * Esta classe contém os dados necessários para criar um empréstimo, incluindo
 * os IDs dos livros a serem emprestados e o ID do usuário que está realizando o empréstimo.
 */
public class CreateLoanRequest {

    private List<Long> bookIds; // Lista de IDs dos livros a serem emprestados
    private Long userId; // ID do usuário que realiza o empréstimo
    
    /**
     * Construtor padrão.
     */
    public CreateLoanRequest() {
    }
    
    /**
     * Construtor que inicializa os campos com os valores fornecidos.
     * 
     * @param bookIds Lista de IDs dos livros a serem emprestados.
     * @param userId ID do usuário que realiza o empréstimo.
     */
    public CreateLoanRequest(List<Long> bookIds, Long userId) {
        this.bookIds = bookIds;
        this.userId = userId;
    }

    /**
     * Obtém a lista de IDs dos livros.
     * 
     * @return A lista de IDs dos livros.
     */
    public List<Long> getBookIds() {
        return bookIds;
    }

    /**
     * Define a lista de IDs dos livros.
     * 
     * @param bookIds A lista de IDs dos livros a ser definida.
     */
    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    /**
     * Obtém o ID do usuário.
     * 
     * @return O ID do usuário.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Define o ID do usuário.
     * 
     * @param userId O ID do usuário a ser definido.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
