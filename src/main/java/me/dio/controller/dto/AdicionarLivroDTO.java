package me.dio.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class AdicionarLivroDTO {
	
	@NotEmpty
	private String titulo;

	@NotNull
    private int edicao;
    
	@NotNull
    private int ano;

	@NotNull
    private int numeroDePaginas;
    
	@NotEmpty
    private String isbn;

	@NotNull
    private Long idEditora;

	@NotNull
	@Size(min = 1)
    private List<Long> idAutores;
	
}
