# Library Management System

O **Library Management System** é uma aplicação para gerenciar empréstimos de livros, permitindo que usuários possam emprestar e devolver livros de uma biblioteca. Este projeto foi desenvolvido utilizando Spring Boot e PostgreSQL.

## Features

- Cadastro de usuários
- Cadastro de livros
- Empréstimo de livros
- Devolução de livros
- Listagem de livros disponíveis e emprestados
- Relatório de empréstimos

...

## Documentação

### Diagramas UML

O diagrama UML fornecem uma visão geral da arquitetura e dos componentes do sistema.

- [Domain Model](docs/uml/Domain Model.png)

## Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas:

- [Java 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [PostgreSQL](https://www.postgresql.org/download/)
- [PgAdmin](https://www.pgadmin.org/download/)

## Instalação

1. Clone o repositório:

    ```bash
    git clone https://github.com/seu-usuario/library_management.git
    cd library_management
    ```

2. Configure o banco de dados PostgreSQL:

    - Crie um banco de dados chamado `library_management_db`.
    - Atualize o arquivo `src/main/resources/application-test.properties` com suas credenciais do PostgreSQL.

3. Execute as migrações do banco de dados:

    ```bash
    mvn clean install
    ```

4. Execute a aplicação:

    ```bash
    mvn spring-boot:run
    ```

## Uso

### Endpoints Principais

- **Criar Usuário**
    - **POST** `/users`
    - Request Body:
    ```json
    {
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
    ```

- **Criar Livro**
    - **POST** `/books`
    - Request Body:
    ```json
    {
      "title": "Effective Java",
      "author": "Joshua Bloch"
    }
    ```

- **Empréstimo de Livro**
    - **POST** `/loans`
    - Request Body:
    ```json
    {
      "userId": 1,
      "bookIds": [1, 2]
    }
    ```

- **Devolução de Livro**
    - **POST** `loans/return`
    - Request Body:
    ```json
    {
      "userId": 1,
      "bookIds": [1]
    }
    ```
    
## Contribuição

Contribuições são bem-vindas! Por favor, siga os passos abaixo para contribuir:

1. Fork o repositório
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Comite suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça o push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
