package com.br.stefanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.stefanini.model.Categoria;
import com.br.stefanini.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<Categoria> findAll() {
		return this.categoriaService.findAll();
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public Categoria save(@RequestBody Categoria categoria) {
		return categoriaService.save(categoria);
	}
	
}
