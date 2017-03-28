package com.br.stefanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.stefanini.model.Editora;
import com.br.stefanini.service.EditoraService;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

	@Autowired
	private EditoraService editoraService;
	
	@GetMapping
	public List<Editora> findAll() {
		return this.editoraService.findAll();
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public Editora save(@RequestBody Editora editora) {
		return editoraService.save(editora);
	}
	
}
