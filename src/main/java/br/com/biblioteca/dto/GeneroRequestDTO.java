package br.com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GeneroRequestDTO(
    
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres.")
    String nome,


    @NotBlank(message = "Sigla é obrigatória.")
    @Size(min = 3,max = 3, message = "Sigla deve conter no maximo 3 caracteres.")
    String sigla
) {
    
}
