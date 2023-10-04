package me.dio.service;

import java.util.List;

import me.dio.controller.dto.AdicionarAutorDTO;
import me.dio.controller.dto.AtualizarAutorDTO;
import me.dio.controller.dto.AutorDTO;
import me.dio.controller.dto.RespostaDTO;

public interface AutorService {

    public List<AutorDTO> listar();

    public AutorDTO pegar(Long idAutor);

    public AutorDTO adicionar(AdicionarAutorDTO adicionarAutorDTO);

    public AutorDTO atualizar(Long idAutor, AtualizarAutorDTO atualizarAutorDTO);
	
	public RespostaDTO remover(Long idAutor);

}
