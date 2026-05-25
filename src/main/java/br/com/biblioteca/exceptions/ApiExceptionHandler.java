package br.com.biblioteca.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//captura erro de todos os controllers
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String campo =((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        ErroResposta resposta = new ErroResposta(400, "Erro de validação", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
    // captura exception customizadas com enum
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroResposta> handleApiException(ApiException ex){
        ErroResposta resposta = new ErroResposta(ex.getErro().getStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getErro().getStatus()).body(resposta);
    }
    //captura qualquer outro erro não tratado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleInternalError(Exception ex){
        ErroResposta resposta = new ErroResposta(500,"Erro interno do servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

}
