# 📚 Library Management
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/rtrnonato/library-management/blob/main/LICENSE)

---

## 📖 Sobre o Projeto

Library Management é uma aplicação backend desenvolvida com Spring Boot e integrada ao banco de dados PostgreSQL. Seu objetivo é permitir operações como o cadastro de livros, gerenciamento de usuários e empréstimos.

### Funcionalidades principais:

- Cadastro de usuários
- Cadastro de livros
- Empréstimo de livros
- Devolução de livros

---

# 📚 Documentação
você pode acessar a documentação interativa da API via Swagger:
https://library-management-c3c3.onrender.com/swagger-ui/index.html#/

---

# 🛠️ Tecnologias Utilizadas

- **Java 17**: Linguagem de programação.
- **Spring Boot 3.2.1**: Framework para desenvolvimento backend.
- **PostgreSQL**: Banco de dados relacional.
- **Hibernate**: ORM para mapeamento de entidades.
- **Swagger/OpenAPI**: Documentação interativa.
- **Maven**: Gerenciador de dependências.
- **Render**: Implantação em produção.
  
---

# 🚀 Como Executar o Projeto

## Pré-requisitos

1. **Java 17** instalado.
2. **PostgreSQL** instalado e rodando.
3. **Maven** configurado no sistema.

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
---

## 👤 Autor

Artur Nonato de Macedo

- 💼 **LinkedIn**: https://www.linkedin.com/in/arturnonato
- 📧 **E-mail**: rtrnonato@gmail.com
- 🌐 **GitHub**: https://github.com/rtrnonato
- 🕗 **WakaTime**: https://wakatime.com/@018bca24-1de0-4f19-91a5-3a111557f794
