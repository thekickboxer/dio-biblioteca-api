package me.dio.controller.exception;

import java.time.OffsetDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import me.dio.service.exception.RegraDeNegocioException;
import me.dio.controller.dto.RespostaErroDTO;
import me.dio.service.exception.EntidadeNaoEncontradaException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RegraDeNegocioException.class)
	public ResponseEntity<Object> handleRegraDeNegocio(RegraDeNegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		RespostaErroDTO respostaErroDTO = criarRespostaErro(ex.getMessage(), status.value());
		
		return handleExceptionInternal(ex, respostaErroDTO, new HttpHeaders(), status, request);
		
	}

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		RespostaErroDTO respostaErroDTO = criarRespostaErro(ex.getMessage(), status.value());
		
		return handleExceptionInternal(ex, respostaErroDTO, new HttpHeaders(), status, request);
		
	}

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleUnexpectedException(Throwable ex, WebRequest request) {
        
        return handleUnexpectedException(new Exception(ex), request);

    }

    @ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnexpectedException(Exception ex, WebRequest request) {
		
		LOGGER.error("Erro", ex);
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		RespostaErroDTO respostaErroDTO = criarRespostaErro("Ocorreu um erro. Tente novamente mais tarde.", status.value());
		
		return handleExceptionInternal(ex, respostaErroDTO, new HttpHeaders(), status, request);
		
	}

    private RespostaErroDTO criarRespostaErro(String mensagem, Integer codigoStatus, List<RespostaErroDTO.Campo> campos) {

		return RespostaErroDTO.builder()
				.dataHora(OffsetDateTime.now())
				.codigoStatus(codigoStatus)
				.mensagem(mensagem)				
				.campos(campos).build();

	}

	private RespostaErroDTO criarRespostaErro(String mensagem, Integer codigoStatus) {

		return criarRespostaErro(mensagem, codigoStatus, null);

	}

}

