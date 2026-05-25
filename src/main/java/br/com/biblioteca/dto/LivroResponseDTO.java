package br.com.biblioteca.dto;

import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.enums.GeneroEnum;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String isbn,
    Integer anoPublicacao,
    GeneroEnum genero,
    String autorNome
) {
    public LivroResponseDTO(Livro livro){
        this(livro.getId(),
        livro.getTitulo(),
        livro.getIsbn(),
        livro.getAnoPublicacao(),
        livro.getGenero(),
        livro.getAutor().getNome()//acesso autor antes e depois o nome.
        );
    }
    
}
