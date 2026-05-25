package br.com.biblioteca.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;



public record AutorRequestDTO(

    @NotBlank(message = "Nome não pode ser nulo.")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres.")
    String nome,

    @NotBlank(message = "Nacionalidade não pode ser nulo.")
    @Size(max = 50, message = "Nacionalidade deve conter no máximo 50 caracteres.")
    String nacionalidade,

    @NotNull(message = "Data de nascimento é obrigatório.")
    @Past(message = "Data de nascimento deve ser no passado.")
    LocalDate dataNascimento
   

) {
    
}
