package br.com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.biblioteca.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByIsbn(String isbn);
    boolean existsByGeneroId(Long generoId);
    boolean existsByEditoraId(Long editoraId);
    boolean existsByAutorId(Long autorId);
    
}
