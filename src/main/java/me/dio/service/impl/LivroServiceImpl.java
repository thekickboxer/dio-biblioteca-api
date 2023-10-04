package me.dio.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.dio.controller.dto.AdicionarLivroDTO;
import me.dio.controller.dto.AtualizarLivroDTO;
import me.dio.controller.dto.AutorDTO;
import me.dio.controller.dto.EditoraDTO;
import me.dio.controller.dto.LivroDTO;
import me.dio.controller.dto.RespostaDTO;
import me.dio.domain.model.Autor;
import me.dio.domain.model.Editora;
import me.dio.domain.model.Livro;
import me.dio.domain.repository.LivroRepository;
import me.dio.service.AutorService;
import me.dio.service.EditoraService;
import me.dio.service.LivroService;
import me.dio.service.exception.EntidadeNaoEncontradaException;

@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
@Getter
@Setter
@Service
public class LivroServiceImpl implements LivroService {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(LivroServiceImpl.class);
	
	private final LivroRepository livroRepository;

	private final EditoraService editoraService;
	private final AutorService autorService;
	
	private final ModelMapper modelMapper;
	
	@Override
	public List<LivroDTO> listar() {
		
		List<LivroDTO> livros = livroRepository.findAll()
			.stream()
			.map(livro -> modelMapper.map(livro, LivroDTO.class))
			.collect(Collectors.toList());
		
		return livros;
		
	}
		
	@Override
	public LivroDTO pegar(Long id) {
		
		// pega a livro que quer (ou lança uma exceção se a mesma não existir)
		Livro livro = livroRepository.findById(id)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhum livro com o id " + id));
		
		LivroDTO livroDTO = modelMapper.map(livro, LivroDTO.class);
		
		return livroDTO;
		
	}

	@Override
	public LivroDTO adicionar(AdicionarLivroDTO adicionarLivroDTO) {
		
		Livro livroParaAdicionar = modelMapper.map(adicionarLivroDTO, Livro.class);

		// pega a editora
		EditoraDTO editoraDTO = editoraService.pegar(adicionarLivroDTO.getIdEditora());

		livroParaAdicionar.setEditora(
			Editora
				.builder()
				.id(editoraDTO.getId())
				.build()
		);

		// pega os autores
		List<Autor> autores = new ArrayList<Autor>();

		AutorDTO autorDTO = null;

		for(Long idAutor : adicionarLivroDTO.getIdAutores()) {

			autorDTO = autorService.pegar(idAutor);

			autores.add(
				Autor
					.builder()
					.id(autorDTO.getId())
					.build()
			);

		}

		livroParaAdicionar.setAutores(autores);
				
		Livro livroAdicionado = livroRepository.save(livroParaAdicionar);
		
		return modelMapper.map(livroAdicionado, LivroDTO.class);
		
	}
	
	@Override
	public LivroDTO atualizar(Long idLivro, AtualizarLivroDTO atualizarLivroDTO) {
		
		// pega a livro que quer (ou lança uma exceção se a mesma não existir)
		Livro livroParaAtualizar = livroRepository.findById(idLivro)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhum livro com o id " + idLivro));

		livroParaAtualizar.setTitulo(atualizarLivroDTO.getTitulo());
		livroParaAtualizar.setAno(atualizarLivroDTO.getAno());
		livroParaAtualizar.setEdicao(atualizarLivroDTO.getEdicao());
		livroParaAtualizar.setNumeroDePaginas(atualizarLivroDTO.getNumeroDePaginas());
		livroParaAtualizar.setIsbn(atualizarLivroDTO.getIsbn());

		// pega a editora
		EditoraDTO editoraDTO = editoraService.pegar(atualizarLivroDTO.getIdEditora());

		livroParaAtualizar.setEditora(
			Editora
				.builder()
				.id(editoraDTO.getId())
				.build()
		);

		// pega os autores
		List<Autor> autores = new ArrayList<Autor>();

		AutorDTO autorDTO = null;

		for(Long idAutor : atualizarLivroDTO.getIdAutores()) {

			autorDTO = autorService.pegar(idAutor);
			
			autores.add(
				Autor
					.builder()
					.id(autorDTO.getId())
					.build()
			);

		}

		livroParaAtualizar.setAutores(autores);
		
		Livro livroAtualizado = livroRepository.save(livroParaAtualizar);
		
        return modelMapper.map(livroAtualizado, LivroDTO.class);
		
	}

	@Override
	public RespostaDTO remover(Long idLivro) {
		
		// pega a livro que quer (ou lança uma exceção se a mesma não existir)
		Livro livro = livroRepository.findById(idLivro)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe nenhum livro com o id " + idLivro));
		
		livroRepository.deleteById(livro.getId());
		
		return criarResposta("Livro removido com sucesso!", HttpStatus.NO_CONTENT.value());
		
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
