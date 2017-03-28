package com.br.stefanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.stefanini.model.Livro;
import com.br.stefanini.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@GetMapping
	public List<Livro> findAll() {
		return this.livroService.findAll();
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public Livro save(@RequestBody Livro livro) {
		return livroService.save(livro);
	}
	
}
