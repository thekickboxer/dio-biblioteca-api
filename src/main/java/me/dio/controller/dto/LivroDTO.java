package me.dio.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroDTO {

    private Long id;

    private String titulo;
    
    private int edicao;
    
    private int ano;

    private int numeroDePaginas;
    
    private String isbn;

    private EditoraDTO editora;

    private List<AutorDTO> autores;
    
}
