package br.com.biblioteca.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditoraRequestDTO(
    
    @NotBlank(message = "Nome não pode ser nulo.")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres.")
    String nome,

    @NotBlank(message = "CNPJ é obrigatório.")
    @CNPJ(message = "CNPJ inválido.")
    @Size(min = 14,max = 18, message = "CNPJ deve conter entre 14 e 18 caracteres.")
    String cnpj,

    @NotBlank(message = "Estado é obrigatório.")
    @Size(min = 2, max = 2, message = "Estado deve  2 caracteres.")
    String estado
) {
    
}
