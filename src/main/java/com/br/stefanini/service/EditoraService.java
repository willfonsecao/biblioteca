package com.br.stefanini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.stefanini.model.Editora;
import com.br.stefanini.repository.EditoraRepository;

@Service
public class EditoraService {

	@Autowired
	private EditoraRepository editoraRepository;
	
	public List<Editora> findAll() {
		return this.editoraRepository.findAll();
	}
	
	public Editora save(Editora editora) {
		return this.editoraRepository.save(editora);
	}
	
}
