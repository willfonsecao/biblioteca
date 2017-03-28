package com.br.stefanini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.stefanini.model.Categoria;
import com.br.stefanini.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return this.categoriaRepository.findAll();
	}
	
	public Categoria save(Categoria categoria) {
		return this.categoriaRepository.save(categoria);
	}
	
}
