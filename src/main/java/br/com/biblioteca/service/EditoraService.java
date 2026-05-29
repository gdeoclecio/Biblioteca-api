package br.com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.dto.EditoraRequestDTO;
import br.com.biblioteca.dto.EditoraResponseDTO;
import br.com.biblioteca.entity.Editora;
import br.com.biblioteca.exceptions.ApiException;
import br.com.biblioteca.exceptions.ErroEnum;
import br.com.biblioteca.repository.EditoraRepository;
import br.com.biblioteca.repository.LivroRepository;

@Service
public class EditoraService {
    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private LivroRepository livroRepository;

    //POST
    public EditoraResponseDTO inserir(EditoraRequestDTO editoraRequest){
        if (editoraRepository.existsByNome(editoraRequest.nome())) {
            throw new ApiException(ErroEnum.EDITORA_JA_CADASTRADA);
        }
        if (editoraRepository.existsByCnpj(editoraRequest.cnpj())) {
            throw new ApiException(ErroEnum.EDITORA_JA_CADASTRADA);
            
        }
        Editora editora = new Editora(
            editoraRequest.nome(),
            editoraRequest.cnpj(),
            editoraRequest.estado()
        );
        Editora editoraSalvo = editoraRepository.save(editora);
        return new EditoraResponseDTO(editoraSalvo);
    }
    //BUSCAR EDITORA POR ID(get)
    public EditoraResponseDTO buscar(Long id){
        Editora editora = editoraRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.EDITORA_NAO_ENCONTRADA));
        
        return new EditoraResponseDTO(editora);
    }
    //LISTAR TODAS AS EDITORAS(GET)
    public List<EditoraResponseDTO> listar(){
        return editoraRepository.findAll().stream().map(EditoraResponseDTO::new).toList();
    }
    //Atualizar editora(put)
    public EditoraResponseDTO atualizar(Long id, EditoraRequestDTO editoraRequest){
        Editora editoraExistente = editoraRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.EDITORA_NAO_ENCONTRADA));

        editoraExistente.setNome(editoraRequest.nome());
        editoraExistente.setCnpj(editoraRequest.cnpj());
        editoraExistente.setEstado(editoraRequest.estado());

        Editora editoraAtualizada = editoraRepository.save(editoraExistente);

        return new EditoraResponseDTO(editoraAtualizada);
    }
    //DELETAR
    public void deletar(Long id){
     editoraRepository.findById(id).orElseThrow(() -> new ApiException(ErroEnum.EDITORA_NAO_ENCONTRADA));
        if (livroRepository.existsByEditoraId(id)) {
            throw new ApiException(ErroEnum.EDITORA_POSSUI_LIVROS);
        }
        editoraRepository.deleteById(id);
    }


}
