package me.dio.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.dio.controller.dto.AdicionarAutorDTO;
import me.dio.controller.dto.AtualizarAutorDTO;
import me.dio.controller.dto.AutorDTO;
import me.dio.controller.dto.RespostaDTO;
import me.dio.domain.model.Autor;
import me.dio.domain.repository.AutorRepository;
import me.dio.service.AutorService;
import me.dio.service.exception.EntidadeNaoEncontradaException;

@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
@Getter
@Setter
@Service
public class AutorServiceImpl implements AutorService {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AutorServiceImpl.class);
	
	private final AutorRepository autorRepository;
	
	private final ModelMapper modelMapper;
	
	@Override
	public List<AutorDTO> listar() {
		
		List<AutorDTO> autores = autorRepository.findAll()
			.stream()		
			.map(autor -> modelMapper.map(autor, AutorDTO.class))
			.collect(Collectors.toList());
		
		return autores;
		
	}
		
	@Override
	public AutorDTO pegar(Long id) {
		
		// pega a autor que quer (ou lança uma exceção se a mesma não existir)
		Autor autor = autorRepository.findById(id)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhum autor com o id " + id));
		
		AutorDTO autorDTO = modelMapper.map(autor, AutorDTO.class);
		
		return autorDTO;
		
	}

	@Override
	public AutorDTO adicionar(AdicionarAutorDTO adicionarAutorDTO) {
		
		Autor autorParaAdicionar = modelMapper.map(adicionarAutorDTO, Autor.class);
		
		Autor autorAdicionado = autorRepository.save(autorParaAdicionar);
		
		return modelMapper.map(autorAdicionado, AutorDTO.class);
		
	}
	
	@Override
	public AutorDTO atualizar(Long idAutor, AtualizarAutorDTO atualizarAutorDTO) {
		
		// pega a autor que quer (ou lança uma exceção se a mesma não existir)
		Autor autor = autorRepository.findById(idAutor)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhum autor com o id " + idAutor));
		
		Autor autorAtualizado = autorRepository.save(autor);
		
        return modelMapper.map(autorAtualizado, AutorDTO.class);
		
	}

	@Override
	public RespostaDTO remover(Long idAutor) {
		
		// pega a autor que quer (ou lança uma exceção se a mesma não existir)
		Autor autor = autorRepository.findById(idAutor)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhum autor com o id " + idAutor));
		
		autorRepository.deleteById(autor.getId());
		
		return criarResposta("Autor removido com sucesso!", HttpStatus.NO_CONTENT.value());
		
	}
			
	private RespostaDTO criarResposta(String mensagem, Integer codigoStatus) {
		
		return RespostaDTO
				.builder()
				.dataHora(OffsetDateTime.now())
				.mensagem(mensagem)
				.codigoStatus(codigoStatus)
				.build();
			
    }
	
}
