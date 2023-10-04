package me.dio.service;

import java.util.List;

import me.dio.controller.dto.AdicionarEditoraDTO;
import me.dio.controller.dto.AtualizarEditoraDTO;
import me.dio.controller.dto.EditoraDTO;
import me.dio.controller.dto.RespostaDTO;

public interface EditoraService {

    public List<EditoraDTO> listar();

    public EditoraDTO pegar(Long idEditora);

    public EditoraDTO adicionar(AdicionarEditoraDTO adicionarEditoraDTO);

    public EditoraDTO atualizar(Long idEditora, AtualizarEditoraDTO atualizarEditoraDTO);
	
	public RespostaDTO remover(Long idEditora);

}
