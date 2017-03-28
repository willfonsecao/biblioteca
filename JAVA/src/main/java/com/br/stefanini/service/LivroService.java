package com.br.stefanini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.stefanini.model.Livro;
import com.br.stefanini.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	public List<Livro> findAll() {
		return this.livroRepository.findAll();
	}
	
	public Livro save(Livro livro) {
		return this.livroRepository.save(livro);
	}
	
}
