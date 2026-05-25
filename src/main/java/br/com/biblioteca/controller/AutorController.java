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

import br.com.biblioteca.dto.AutorRequestDTO;
import br.com.biblioteca.dto.AutorResponseDTO;
import br.com.biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Autores", description = "Gerenciamento de autores")
@RestController
@RequestMapping("/autores")
public class AutorController {
    @Autowired
    private  AutorService autorService;

// CADASTRAR AUTOR
    @Operation(summary = "Cadastrar autor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Autor criado"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    @PostMapping
    public ResponseEntity<AutorResponseDTO> cadastrar(@RequestBody @Valid AutorRequestDTO autorRequest){
        AutorResponseDTO autor = autorService.inserir(autorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(autor);
    }
// BUSCAR AUTOR POR ID
    @Operation(summary = "Buscar autor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor encontrado"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(autorService.buscar(id));
    }

// LISTAR TODOS OS AUTORES
    @Operation(summary = "Listar todos os autores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros encontrados")
    })
    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listar(){
        List<AutorResponseDTO>autores = autorService.listar();
        return ResponseEntity.ok(autores);
    }
    // ATUALIZAR AUTOR
    @Operation(summary = "Atualizar autor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor atualizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AutorRequestDTO autorRequest){
       return ResponseEntity.ok(autorService.atualizar(id, autorRequest));
    }

    //DELETAR AUTOR
    @Operation(summary = "Excluir autor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Autor removido"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar (@PathVariable Long id){
        autorService.deletar(id);
        return ResponseEntity.noContent().build();// para retornar 204 no content
    }
    
}
