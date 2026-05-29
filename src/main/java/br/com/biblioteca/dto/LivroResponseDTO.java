package br.com.biblioteca.dto;

import br.com.biblioteca.entity.Livro;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String isbn,
    Integer anoPublicacao,
    Long generoId,
    String generoNome,
    Long editoraId,
    String editoraNome,
    String autorNome,
    Long autorId
) {
    public LivroResponseDTO(Livro livro){
        this(livro.getId(),
        livro.getTitulo(),
        livro.getIsbn(),
        livro.getAnoPublicacao(),

        livro.getGenero().getId(),
        livro.getGenero().getNome(),

        livro.getEditora().getId(),
        livro.getEditora().getNome(),
        
        livro.getAutor().getNome(),
        livro.getAutor().getId() //acesso autor antes e depois o nome.
        );
    }
    
}
