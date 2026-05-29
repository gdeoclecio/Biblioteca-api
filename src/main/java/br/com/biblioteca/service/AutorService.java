package br.com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.dto.AutorRequestDTO;
import br.com.biblioteca.dto.AutorResponseDTO;
import br.com.biblioteca.entity.Autor;
import br.com.biblioteca.exceptions.ApiException;
import br.com.biblioteca.exceptions.ErroEnum;
import br.com.biblioteca.repository.AutorRepository;
import br.com.biblioteca.repository.LivroRepository;


@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    //(Post)
  
    public AutorResponseDTO inserir(AutorRequestDTO autorRequest){
        if(autorRepository.existsByNome(autorRequest.nome())){
            throw new ApiException(ErroEnum.AUTOR_JA_CADASTRADO);
        }

        Autor autor = new Autor(
            autorRequest.nome(),
            autorRequest.nacionalidade(),
            autorRequest.dataNascimento()
        );
       
        Autor autorSalvo = autorRepository.save(autor);

        return new AutorResponseDTO(autorSalvo);
    }
    
    //buscar autor por id(get)
    public AutorResponseDTO buscar(Long id){

        Autor autor = autorRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_NAO_ENCONTRADO));

        return new AutorResponseDTO(autor);
    }
    // listar todos os autores(get)
    public List<AutorResponseDTO> listar(){

        return autorRepository.findAll().stream().map(AutorResponseDTO::new).toList();
    }
    // atualizar autor(put)
    public AutorResponseDTO atualizar(Long id, AutorRequestDTO autorRequest){

        Autor autorExistente = autorRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_NAO_ENCONTRADO));

        autorExistente.setNome(autorRequest.nome());
        autorExistente.setNacionalidade(autorRequest.nacionalidade());
        autorExistente.setDataNascimento(autorRequest.dataNascimento());

        Autor autorAtualizado = autorRepository.save(autorExistente);

        return new AutorResponseDTO(autorAtualizado);
    }
    //Deletar autor(DELETE)
    public void deletar(Long id){
        
       autorRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.AUTOR_NAO_ENCONTRADO));
        if (livroRepository.existsByAutorId(id)) {
            throw new ApiException(ErroEnum.AUTOR_POSSUI_LIVROS);
        }
        autorRepository.deleteById(id);
    }
}
