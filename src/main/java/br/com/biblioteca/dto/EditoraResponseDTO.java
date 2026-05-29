package br.com.biblioteca.dto;

import br.com.biblioteca.entity.Editora;

public record EditoraResponseDTO(
    Long id,
    String nome,
    String estado
) {
    public EditoraResponseDTO(Editora editora){
        this(editora.getId(),
        editora.getNome(),
        editora.getEstado()
        );
    }
    
}
