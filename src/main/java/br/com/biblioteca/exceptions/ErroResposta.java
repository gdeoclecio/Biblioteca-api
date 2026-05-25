package br.com.biblioteca.exceptions;

import java.util.Map;

public class ErroResposta {
    private int status;
    private String mensagem;
    private Map<String, String> erros;

    public ErroResposta(int status, String mensagem, Map<String, String> erros){
        this.status = status;
        this.mensagem = mensagem;
        this.erros = erros;
    }
    //construtor para erros simples(sem map)
    public ErroResposta(int status, String mensagem){
        this.status = status;
        this.mensagem = mensagem;
        this.erros = null;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Map<String, String> getErros() {
        return erros;
    }
    
}
