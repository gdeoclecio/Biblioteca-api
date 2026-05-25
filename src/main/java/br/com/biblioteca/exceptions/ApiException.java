package br.com.biblioteca.exceptions;

public class ApiException extends RuntimeException {
    private final ErroEnum erro;

    public ApiException(ErroEnum erro){
        super(erro.getMensagem());
        this.erro = erro;
    }
    public ApiException(ErroEnum erro, String mensagem){
        super(mensagem);
        this.erro = erro;
    }
    public ErroEnum getErro(){
        return erro;
    }
    
}
