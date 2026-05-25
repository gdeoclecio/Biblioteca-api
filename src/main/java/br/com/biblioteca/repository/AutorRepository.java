package br.com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.biblioteca.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNome(String nome);
    
}
