package br.com.biblioteca.exceptions;

import org.springframework.http.HttpStatus;


//Enum para erros que passariam pelo @Valid
public enum ErroEnum {
    AUTOR_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Autor não encontrado."),
    LIVRO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Livro não encontrado."),
    AUTOR_INVALIDO(HttpStatus.BAD_REQUEST, "Autor informado não existe."),
    AUTOR_POSSUI_LIVROS(HttpStatus.BAD_REQUEST,"Autor possui livros cadastrados."),
    AUTOR_JA_CADASTRADO(HttpStatus.BAD_REQUEST,"Autor já cadastrado"),
    LIVRO_JA_CADASTRADO(HttpStatus.BAD_REQUEST, "Livro já cadastrado");

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
