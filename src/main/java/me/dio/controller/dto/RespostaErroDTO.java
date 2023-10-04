package me.dio.controller.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespostaErroDTO {

	private Integer codigoStatus;
	private OffsetDateTime dataHora;
	private String mensagem;
	private List<Campo> campos;
	
	@AllArgsConstructor
	@Getter
	public static class Campo {
		
		private String nome;
		private String mensagem;
		
	}
	
}
