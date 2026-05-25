package br.com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.dto.LivroRequestDTO;
import br.com.biblioteca.dto.LivroResponseDTO;
import br.com.biblioteca.entity.Autor;
import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.exceptions.ApiException;
import br.com.biblioteca.exceptions.ErroEnum;
import br.com.biblioteca.repository.AutorRepository;
import br.com.biblioteca.repository.LivroRepository;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    //(Post)
    public LivroResponseDTO inserir (LivroRequestDTO livroRequest){
        if(livroRepository.existsByIsbn(livroRequest.isbn())){
            throw new ApiException(ErroEnum.LIVRO_JA_CADASTRADO);
        }
        Autor autor = autorRepository.findById(livroRequest.autorId()).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_INVALIDO));

        Livro livro = new Livro(
            livroRequest.titulo(),
            livroRequest.isbn(),
            livroRequest.anoPublicacao(),
            livroRequest.genero(),
            autor
        );
        Livro livroSalvo = livroRepository.save(livro);
    
        return new LivroResponseDTO(livroSalvo);
    }
    //buscar livro por id(get)
    public LivroResponseDTO buscar(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.LIVRO_NAO_ENCONTRADO));
        return new LivroResponseDTO(livro);
    }
    //Listar todos os livros(get)
    public List<LivroResponseDTO> listar(){
        return livroRepository.findAll().stream().map(LivroResponseDTO::new).toList();
    }
    //atualizar LIVRO(PUT)
    public LivroResponseDTO atualizar(Long id, LivroRequestDTO livroRequest){
        Livro livroExistente = livroRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.LIVRO_NAO_ENCONTRADO));

        Autor autor = autorRepository.findById(livroRequest.autorId()).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_INVALIDO));

        livroExistente.setTitulo(livroRequest.titulo());
        livroExistente.setIsbn(livroRequest.isbn());
        livroExistente.setAnoPublicacao(livroRequest.anoPublicacao());
        livroExistente.setGenero(livroRequest.genero());
        livroExistente.setAutor(autor);
         Livro livroAtualizado = livroRepository.save(livroExistente);
         return new LivroResponseDTO(livroAtualizado);
    }
    // deletar um livro(DELETE)
    public void deletar(Long id){
         livroRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.LIVRO_NAO_ENCONTRADO));
        livroRepository.deleteById(id);;
    }
}
