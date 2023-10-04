package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import me.dio.controller.dto.AdicionarLivroDTO;
import me.dio.controller.dto.AtualizarLivroDTO;
import me.dio.controller.dto.LivroDTO;
import me.dio.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor()
@CrossOrigin
@RestController
@RequestMapping("/livros")
@Tag(name = "Livro Controller", description = "RESTful API para gerenciar livros.")
public class LivroController {

    private LivroService livroService;

    @GetMapping
    @Operation(summary = "Pega todos os livros", description = "Recupera uma lista com todos os livros cadastrados")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    })
    public ResponseEntity<List<LivroDTO>> listar() {

        List<LivroDTO> livros = livroService.listar();
        return ResponseEntity.ok(livros);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Pega um livro pelo ID", description = "Recupera um livro específico baseado pelo ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroDTO> pegar(@PathVariable Long id) {

        LivroDTO livro = livroService.pegar(id);
        return ResponseEntity.ok(livro);

    }

    @PostMapping
    @Operation(summary = "Cria um novo livro", description = "Cria um novo livro e retorna seus dados")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<LivroDTO> create(@RequestBody AdicionarLivroDTO adicionarLivroDTO) {

        LivroDTO livro = livroService.adicionar(adicionarLivroDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livro.getId())
                .toUri();

        return ResponseEntity.created(location).body(livro);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um livro", description = "Atualiza os dados de um livro existente baseado no seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody AtualizarLivroDTO atualizarLivroDTO) {

        LivroDTO livro = livroService.atualizar(id, atualizarLivroDTO);
        return ResponseEntity.ok(livro);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma livro", description = "Exclui um livro existente baseado no seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        livroService.remover(id);
        return ResponseEntity.noContent().build();

    }
}
