package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import me.dio.controller.dto.AdicionarEditoraDTO;
import me.dio.controller.dto.AtualizarEditoraDTO;
import me.dio.controller.dto.EditoraDTO;
import me.dio.service.EditoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor()
@CrossOrigin
@RestController
@RequestMapping("/editoras")
@Tag(name = "Editora Controller", description = "RESTful API para gerenciar editoras.")
public class EditoraController {

    private EditoraService editoraService;

    @GetMapping
    @Operation(summary = "Pega todas as editoras", description = "Recupera uma lista com todas as editoras cadastradas")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    })
    public ResponseEntity<List<EditoraDTO>> listar() {

        List<EditoraDTO> editoras = editoraService.listar();
        return ResponseEntity.ok(editoras);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Pega uma editora pelo ID", description = "Recupera uma editora específica baseado pelo ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<EditoraDTO> pegar(@PathVariable Long id) {

        EditoraDTO editora = editoraService.pegar(id);
        return ResponseEntity.ok(editora);

    }

    @PostMapping
    @Operation(summary = "Cria uma nova editora", description = "Cria uma nova editora e retorna seus dados")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Editora criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<EditoraDTO> create(@RequestBody AdicionarEditoraDTO adicionarEditoraDTO) {

        EditoraDTO editora = editoraService.adicionar(adicionarEditoraDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(editora.getId())
                .toUri();

        return ResponseEntity.created(location).body(editora);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma editora", description = "Atualiza os dados de uma editora existente baseado no seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Editora atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<EditoraDTO> update(@PathVariable Long id, @RequestBody AtualizarEditoraDTO atualizarEditoraDTO) {

        EditoraDTO editora = editoraService.atualizar(id, atualizarEditoraDTO);
        return ResponseEntity.ok(editora);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma editora", description = "Exclui uma editora existente baseado no seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Editora excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        editoraService.remover(id);
        return ResponseEntity.noContent().build();

    }
}
