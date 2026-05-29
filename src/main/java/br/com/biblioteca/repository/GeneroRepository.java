package br.com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.biblioteca.entity.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
    boolean existsByNome(String nome);
    boolean existsBySigla(String sigla);
}
