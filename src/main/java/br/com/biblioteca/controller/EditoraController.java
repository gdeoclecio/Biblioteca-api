package br.com.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.biblioteca.dto.EditoraRequestDTO;
import br.com.biblioteca.dto.EditoraResponseDTO;
import br.com.biblioteca.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Editoras", description = "Gerenciamento de editoras")
@RestController
@RequestMapping("/editoras")
public class EditoraController {
    @Autowired
    private EditoraService editoraService;

    //CADASTRAR EDITORA
    @Operation(summary = "Cadastrar editora")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Editora criada"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    @PostMapping
    public ResponseEntity<EditoraResponseDTO> cadastrar(@RequestBody @Valid EditoraRequestDTO editoraRequest){
        EditoraResponseDTO editora = editoraService.inserir(editoraRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(editora);
    }
    //BUSCAR AUTOR POR ID
    @Operation(summary = "Buscar editora por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Editora encontrada"),
        @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(editoraService.buscar(id));
    }
    //LISTAR TODOS OS AUTORES
    @Operation(summary = "Listar todos as editoras ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros encontrados")
    })
    @GetMapping
    public ResponseEntity<List<EditoraResponseDTO>> listar(){
        List<EditoraResponseDTO> editoras = editoraService.listar();
        return ResponseEntity.ok(editoras);
    }
    //ATUALIZAR EDITORAS
    @Operation(summary = "Atualizar editora")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Editora atualizada"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
        @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EditoraRequestDTO editoraRequest){
        return ResponseEntity.ok(editoraService.atualizar(id, editoraRequest));
    }
    //DELETAR EDITORA
    @Operation(summary = "Excluir editora")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "204", description = "Editora removida"),
        @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        editoraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
