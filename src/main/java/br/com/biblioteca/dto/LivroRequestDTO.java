package br.com.biblioteca.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO(
    
     @NotBlank(message = "Titulo não pode ser nulo.")
     @Size(max = 150, message = "Titulo deve conter no máximo 150 caracteres.")
     String titulo,

     @NotBlank(message = "ISBN não pode ser nulo.")
     @Size( max = 20, message = "ISBN deve conter no máximo 20 caracteres.")
     String isbn,

     @NotNull(message = "Ano de publicação não pode ser nulo.")
     @Max(value = 2026, message = "O ano de publicação não pode ser no futuro.")
     Integer anoPublicacao,

     @NotNull(message = "Gênero não pode ser nulo.")
     Long generoId,

     @NotNull(message = "Editora não pode ser nulo.")
     Long editoraId,

     @NotNull(message = "Autor é obrigatório.")
     Long autorId
) {
    
}
