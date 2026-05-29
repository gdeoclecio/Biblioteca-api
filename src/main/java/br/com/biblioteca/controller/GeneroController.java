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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.biblioteca.dto.GeneroRequestDTO;
import br.com.biblioteca.dto.GeneroResponseDTO;
import br.com.biblioteca.service.GeneroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Generos", description = "Gerenciamento de generos")
@RestController
@RequestMapping("/generos")
public class GeneroController {
    @Autowired
    private GeneroService generoService;

    //CADASTRAR GENERO
    @Operation(summary = "Cadastar genero")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Genero criado"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    @PostMapping
    public ResponseEntity <GeneroResponseDTO> cadastrar(@RequestBody @Valid GeneroRequestDTO generoRequest){
        GeneroResponseDTO genero = generoService.inserir(generoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(genero);
    }
    //BUSCAR GENERO POR ID
    @Operation(summary = "Buscar genero por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero encontrado"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity <GeneroResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(generoService.buscar(id));
    }
    //LISTAR TODOS OS GENEROS
    @Operation(summary = "Listar todos os generos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros encontrados")
    })
    @GetMapping
    public ResponseEntity<List<GeneroResponseDTO>> listar(){
        List<GeneroResponseDTO> generos = generoService.listar();
        return ResponseEntity.ok(generos);
    }
    //ATUALIZAR AUTOR
    @Operation(summary = "Atualizar genero")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero Atualizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Genero não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity <GeneroResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid GeneroRequestDTO generoRequest){
        return ResponseEntity.ok(generoService.atualizar(id, generoRequest));
    }
    //DELETAR GENERO
    @Operation(summary = "Excluir genero")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Genero removido"),
        @ApiResponse(responseCode = "404", description = "Genero não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        generoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
