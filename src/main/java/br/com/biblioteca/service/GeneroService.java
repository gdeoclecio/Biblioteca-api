package br.com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.dto.GeneroRequestDTO;
import br.com.biblioteca.dto.GeneroResponseDTO;
import br.com.biblioteca.entity.Genero;
import br.com.biblioteca.exceptions.ApiException;
import br.com.biblioteca.exceptions.ErroEnum;
import br.com.biblioteca.repository.GeneroRepository;
import br.com.biblioteca.repository.LivroRepository;

@Service
public class GeneroService {
    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private LivroRepository livroRepository;

    //POST

    public GeneroResponseDTO inserir(GeneroRequestDTO generoRequest){
        if (generoRepository.existsByNome(generoRequest.nome())) {
            throw new ApiException(ErroEnum.GENERO_JA_CADASTRADO);
        }
        if (generoRepository.existsBySigla(generoRequest.sigla())) {
            throw new ApiException(ErroEnum.GENERO_JA_CADASTRADO);
            
        }
        Genero genero = new Genero(
            generoRequest.nome(),
            generoRequest.sigla()
        );
        Genero generoSalvo = generoRepository.save(genero);
        return new GeneroResponseDTO(generoSalvo);
    }

    //Buscar genero por id(get)
    public GeneroResponseDTO buscar(Long id){
        Genero genero = generoRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.GENERO_NAO_ENCONTRADO));

        return new GeneroResponseDTO(genero);
    }
    //Listar todos os generos(get)
    public List<GeneroResponseDTO> listar(){
        return generoRepository.findAll().stream().map(GeneroResponseDTO::new).toList();
    }
    //Atualizar genero(put)
    public GeneroResponseDTO atualizar(Long id, GeneroRequestDTO generoRequest){
        Genero generoExistente = generoRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.GENERO_NAO_ENCONTRADO));

        generoExistente.setNome(generoRequest.nome());
        generoExistente.setSigla(generoRequest.sigla());

        Genero generoAtualizado = generoRepository.save(generoExistente);
        
        return new GeneroResponseDTO(generoAtualizado);
    }
    // Deletar
    public void deletar(Long id){
        generoRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.GENERO_NAO_ENCONTRADO));

        if (livroRepository.existsByGeneroId(id)) {
            throw new ApiException(ErroEnum.GENERO_POSSUI_LIVROS);
        }
        generoRepository.deleteById(id);
    }

}
