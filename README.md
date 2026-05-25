# Biblioteca API

Projeto desenvolvido utilizando Java + Spring Boot com o objetivo de criar uma API REST para gerenciamento de autores e livros.

A aplicação permite realizar operações completas de cadastro, consulta, atualização e remoção de autores e livros, seguindo boas práticas de desenvolvimento backend com arquitetura em camadas, utilização de DTOs, tratamento de exceções, validações com Bean Validation e documentação com Swagger/OpenAPI.

## Funcionalidades

### Autor

* Cadastrar autor
* Buscar autor por ID
* Listar autores
* Atualizar autor
* Excluir autor

### Livro

* Cadastrar livro
* Buscar livro por ID
* Listar livros
* Atualizar livro
* Excluir livro

## Regras de negócio implementadas

* Não permitir cadastro de autor com nome vazio
* Não permitir cadastro de livro sem autor
* Não permitir cadastro de livro com título vazio
* Não permitir vínculo de livro com autor inexistente
* Validar existência de autor e livro em atualizações e exclusões

## Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Swagger/OpenAPI

## Documentação Swagger

A documentação da API pode ser acessada em:

http://localhost:8080/swagger-ui/index.html

## Integrantes do Grupo

* Gabriela Carvalho
* Juliano Coelho
* Daniel Valle
* Mariana Oliveira
.
