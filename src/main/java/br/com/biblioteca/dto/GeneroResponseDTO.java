package br.com.biblioteca.dto;

import br.com.biblioteca.entity.Genero;

public record GeneroResponseDTO(
    Long id,
    String nome,
    String sigla
) {
    public GeneroResponseDTO(Genero genero){
        this(genero.getId(),
        genero.getNome(),
        genero.getSigla()
    );
    }
    
}
