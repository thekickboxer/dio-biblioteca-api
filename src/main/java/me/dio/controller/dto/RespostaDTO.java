package me.dio.controller.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespostaDTO {

	private Integer codigoStatus;
	private OffsetDateTime dataHora;
    private String mensagem;
    
}
