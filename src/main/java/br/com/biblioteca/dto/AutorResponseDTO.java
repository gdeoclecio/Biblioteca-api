package br.com.biblioteca.dto;

import java.time.LocalDate;

import br.com.biblioteca.entity.Autor;


public record AutorResponseDTO(
    Long id,
    String nome,
    String nacionalidade,
    LocalDate dataNascimento
) {
    public AutorResponseDTO(Autor autor){
        this(autor.getId(),
        autor.getNome(),
        autor.getNacionalidade(),
        autor.getDataNascimento()
    );
    }
    
}
