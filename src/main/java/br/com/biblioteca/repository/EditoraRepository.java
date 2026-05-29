package br.com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.biblioteca.entity.Editora;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
    boolean existsByNome(String nome);
    boolean existsByCnpj(String cnpj);
}
