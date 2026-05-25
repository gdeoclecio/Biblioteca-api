package br.com.biblioteca.dto;

import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.enums.GeneroEnum;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String isbn,
    Integer anoPublicacao,
    GeneroEnum genero,
    Long autorId,
    String autorNome
) {
    public LivroResponseDTO(Livro livro){
        this(livro.getId(),
        livro.getTitulo(),
        livro.getIsbn(),
        livro.getAnoPublicacao(),
        livro.getGenero(),
        livro.getAutor().getId(),//Preciso acessar autor antes e depois acesso id.
        livro.getAutor().getNome()//acesso autor antes e depois o nome.
        );
    }
    
}
