package br.com.biblioteca.exceptions;

import org.springframework.http.HttpStatus;


//Enum para erros que passariam pelo @Valid
public enum ErroEnum {
    //Autor
    AUTOR_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Autor não encontrado."),
    AUTOR_INVALIDO(HttpStatus.BAD_REQUEST, "Autor informado não existe."),
    AUTOR_POSSUI_LIVROS(HttpStatus.BAD_REQUEST,"Autor possui livros cadastrados."),
    AUTOR_JA_CADASTRADO(HttpStatus.BAD_REQUEST,"Autor já cadastrado"),

    // Livro
    LIVRO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Livro não encontrado."),
    LIVRO_JA_CADASTRADO(HttpStatus.BAD_REQUEST, "Livro já cadastrado"),

    // Genero
    GENERO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND,"Genero não encontrado"),
    GENERO_INVALIDO(HttpStatus.BAD_REQUEST,"Genero informado não existe"),
    GENERO_JA_CADASTRADO(HttpStatus.BAD_REQUEST,"Genero ja cadastrado"),
    GENERO_POSSUI_LIVROS(HttpStatus.BAD_REQUEST,"Genero possui livros cadastrados"),

    //Editora
    EDITORA_NAO_ENCONTRADA(HttpStatus.NOT_FOUND, "Editora não encontrada"),
    EDITORA_INVALIDA(HttpStatus.BAD_REQUEST, "Editora informada não existe"),
    EDITORA_JA_CADASTRADA(HttpStatus.BAD_REQUEST,"Editora ja cadastrada."),
    EDITORA_POSSUI_LIVROS(HttpStatus.BAD_REQUEST,"Editora possui livros vinculados"),

    //Usuario 
    USUARIO_INVALIDO(HttpStatus.UNAUTHORIZED,"Usuario invalido"),

    //Autenticações
    SENHA_INVALIDA(HttpStatus.UNAUTHORIZED,"Senha invalida"),
    TOKEN_INVALIDO(HttpStatus.UNAUTHORIZED, "Token inválido."),
    TOKEN_EXPIRADO(HttpStatus.UNAUTHORIZED, "Token expirado. Faça login novamente."),
    TOKEN_AUSENTE(HttpStatus.UNAUTHORIZED, "Token não informado."),
    ACESSO_NEGADO(HttpStatus.FORBIDDEN, "Acesso negado. Você não tem permissão para acessar este recurso.");

    private final HttpStatus status;
    private final String mensagem;

    ErroEnum(HttpStatus status, String mensagem){
        this.status = status;
        this.mensagem = mensagem;
    }
    public HttpStatus getStatus(){
        return status;
    }
    public String getMensagem(){
        return mensagem;
    }

}
