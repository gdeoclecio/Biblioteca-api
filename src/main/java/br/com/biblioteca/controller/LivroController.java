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

import br.com.biblioteca.dto.LivroRequestDTO;
import br.com.biblioteca.dto.LivroResponseDTO;
import br.com.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;


@Tag(name = "Livros", description = "Gerenciamento de livros")
@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

// Cadastar livro
    @Operation(summary = "Cadastrar livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro criado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<LivroResponseDTO> cadastrar( @RequestBody @Valid LivroRequestDTO livroRequest){

        LivroResponseDTO livroSalvo = livroService.inserir(livroRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }
// Buscar por ID
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
     public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id) {

        LivroResponseDTO livro = livroService.buscar(id);

        return ResponseEntity.ok(livro);
    }
    // Listar todos os livros
    @Operation(summary = "Listar todos os livros")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros encontrados")
        })
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listar() {
        List<LivroResponseDTO> livros = livroService.listar();
        return ResponseEntity.ok(livros);
    }

// Atualizar livros
    @Operation(summary = "Atualizar livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
        @ApiResponse(responseCode =  "404", description = "Livro não encontrado")
    })
    @PutMapping("/{id}")
     public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LivroRequestDTO livroRequest) {

        LivroResponseDTO livroAtualizado =
                livroService.atualizar(id, livroRequest);

        return ResponseEntity.ok(livroAtualizado);
    }
// Deletar livros
    @Operation(summary = "Excluir livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro removido"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
