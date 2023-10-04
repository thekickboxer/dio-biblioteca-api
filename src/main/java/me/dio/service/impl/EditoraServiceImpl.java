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
import me.dio.controller.dto.AdicionarEditoraDTO;
import me.dio.controller.dto.AtualizarEditoraDTO;
import me.dio.controller.dto.EditoraDTO;
import me.dio.controller.dto.RespostaDTO;
import me.dio.domain.model.Editora;
import me.dio.domain.repository.EditoraRepository;
import me.dio.service.EditoraService;
import me.dio.service.exception.EntidadeNaoEncontradaException;

@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
@Getter
@Setter
@Service
public class EditoraServiceImpl implements EditoraService {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(EditoraServiceImpl.class);
	
	private final EditoraRepository editoraRepository;
	
	private final ModelMapper modelMapper;
	
	@Override
	public List<EditoraDTO> listar() {
		
		List<EditoraDTO> editoras = editoraRepository.findAll()
			.stream()		
			.map(editora -> modelMapper.map(editora, EditoraDTO.class))
			.collect(Collectors.toList());
		
		return editoras;
		
	}
		
	@Override
	public EditoraDTO pegar(Long id) {
		
		// pega a editora que quer (ou lança uma exceção se a mesma não existir)
		Editora editora = editoraRepository.findById(id)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhuma editora com o id " + id));
		
		EditoraDTO editoraDTO = modelMapper.map(editora, EditoraDTO.class);
		
		return editoraDTO;
		
	}

	@Override
	public EditoraDTO adicionar(AdicionarEditoraDTO adicionarEditoraDTO) {
		
		Editora editoraParaAdicionar = modelMapper.map(adicionarEditoraDTO, Editora.class);
		
		Editora editoraAdicionada = editoraRepository.save(editoraParaAdicionar);
		
		return modelMapper.map(editoraAdicionada, EditoraDTO.class);
		
	}
	
	@Override
	public EditoraDTO atualizar(Long idEditora, AtualizarEditoraDTO atualizarEditoraDTO) {
		
		// pega a editora que quer (ou lança uma exceção se a mesma não existir)
		Editora editora = editoraRepository.findById(idEditora)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhuma editora com o id " + idEditora));
		
		Editora editoraAtualizada = editoraRepository.save(editora);
		
        return modelMapper.map(editoraAtualizada, EditoraDTO.class);
		
	}

	@Override
	public RespostaDTO remover(Long idEditora) {
		
		// pega a editora que quer (ou lança uma exceção se a mesma não existir)
		Editora editora = editoraRepository.findById(idEditora)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhuma editora com o id " + idEditora));
		
		editoraRepository.deleteById(editora.getId());
		
		return criarResposta("Editora removida com sucesso!", HttpStatus.NO_CONTENT.value());
		
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
