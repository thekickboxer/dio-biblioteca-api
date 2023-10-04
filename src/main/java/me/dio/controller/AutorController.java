package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import me.dio.controller.dto.AdicionarAutorDTO;
import me.dio.controller.dto.AtualizarAutorDTO;
import me.dio.controller.dto.AutorDTO;
import me.dio.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor()
@CrossOrigin
@RestController
@RequestMapping("/autores")
@Tag(name = "Autor Controller", description = "RESTful API para gerenciar autores.")
public class AutorController {

    private AutorService autorService;

    @GetMapping
    @Operation(summary = "Pega todos os autores", description = "Recupera uma lista com todos os autores cadastrados")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    })
    public ResponseEntity<List<AutorDTO>> listar() {

        List<AutorDTO> autores = autorService.listar();
        return ResponseEntity.ok(autores);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Pega um autor pelo ID", description = "Recupera um autor específico baseado pelo ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<AutorDTO> pegar(@PathVariable Long id) {

        AutorDTO autor = autorService.pegar(id);
        return ResponseEntity.ok(autor);

    }

    @PostMapping
    @Operation(summary = "Cria um novo autor", description = "Cria um novo autor e retorna seus dados")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<AutorDTO> create(@RequestBody AdicionarAutorDTO adicionarAutorDTO) {

        AutorDTO autor = autorService.adicionar(adicionarAutorDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(location).body(autor);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um autor", description = "Atualiza os dados de um autor existente baseado no seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<AutorDTO> update(@PathVariable Long id, @RequestBody AtualizarAutorDTO atualizarAutorDTO) {

        AutorDTO autor = autorService.atualizar(id, atualizarAutorDTO);
        return ResponseEntity.ok(autor);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma autor", description = "Exclui um autor existente baseado no seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        autorService.remover(id);
        return ResponseEntity.noContent().build();

    }
}
