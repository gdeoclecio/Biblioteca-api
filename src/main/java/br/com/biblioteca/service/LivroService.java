package br.com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.dto.LivroRequestDTO;
import br.com.biblioteca.dto.LivroResponseDTO;
import br.com.biblioteca.entity.Autor;
import br.com.biblioteca.entity.Editora;
import br.com.biblioteca.entity.Genero;
import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.exceptions.ApiException;
import br.com.biblioteca.exceptions.ErroEnum;
import br.com.biblioteca.repository.AutorRepository;
import br.com.biblioteca.repository.EditoraRepository;
import br.com.biblioteca.repository.GeneroRepository;
import br.com.biblioteca.repository.LivroRepository;


@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private EmailService emailService;

    //(Post)
    public LivroResponseDTO inserir (LivroRequestDTO livroRequest){
        if(livroRepository.existsByIsbn(livroRequest.isbn())){
            throw new ApiException(ErroEnum.LIVRO_JA_CADASTRADO);
        }
        Autor autor = autorRepository.findById(livroRequest.autorId()).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_INVALIDO));

        Genero genero = generoRepository.findById(livroRequest.generoId()).orElseThrow(() -> new ApiException(ErroEnum.GENERO_INVALIDO));

        Editora editora = editoraRepository.findById(livroRequest.editoraId()).orElseThrow(() -> new ApiException(ErroEnum.EDITORA_INVALIDA));

        Livro livro = new Livro(
            livroRequest.titulo(),
            livroRequest.isbn(),
            livroRequest.anoPublicacao(),
            genero,
            editora,
            autor
        );
        LivroResponseDTO response = new LivroResponseDTO(livroRepository.save(livro));

        emailService.enviarEmailCadastroLivro(response);
        //Livro livroSalvo = livroRepository.save(livro);
           return response;
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
    // LIVRO(PUT)
    public LivroResponseDTO atualizar(Long id, LivroRequestDTO livroRequest){
        Livro livroExistente = livroRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.LIVRO_NAO_ENCONTRADO));

        Autor autor = autorRepository.findById(livroRequest.autorId()).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_INVALIDO));

        Genero genero = generoRepository.findById(livroRequest.generoId()).orElseThrow(() -> new ApiException(ErroEnum.GENERO_INVALIDO));

        Editora editora = editoraRepository.findById(livroRequest.editoraId()).orElseThrow(() -> new ApiException(ErroEnum.EDITORA_INVALIDA));

        livroExistente.setTitulo(livroRequest.titulo());
        livroExistente.setIsbn(livroRequest.isbn());
        livroExistente.setAnoPublicacao(livroRequest.anoPublicacao());

        livroExistente.setGenero(genero);
        livroExistente.setAutor(autor);
        livroExistente.setEditora(editora);

        LivroResponseDTO response = new LivroResponseDTO(livroRepository.save(livroExistente));

        emailService.enviarEmailAtualizacaoLivro(response);
        return response;
         //Livro livroAtualizado = livroRepository.save(livroExistente);
    }
    // deletar um livro(DELETE)
    public void deletar(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.LIVRO_NAO_ENCONTRADO));
         LivroResponseDTO response = new LivroResponseDTO(livro);
        livroRepository.deleteById(id);
        emailService.enviarEmailExclusaoLivro(response);
    }
}
