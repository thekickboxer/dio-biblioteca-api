package me.dio.service;

import java.util.List;

import me.dio.controller.dto.AdicionarLivroDTO;
import me.dio.controller.dto.AtualizarLivroDTO;
import me.dio.controller.dto.LivroDTO;
import me.dio.controller.dto.RespostaDTO;

public interface LivroService {

    public List<LivroDTO> listar();

    public LivroDTO pegar(Long idLivro);

    public LivroDTO adicionar(AdicionarLivroDTO adicionarLivroDTO);

    public LivroDTO atualizar(Long idLivro, AtualizarLivroDTO atualizarLivroDTO);
	
	public RespostaDTO remover(Long idLivro);

}
