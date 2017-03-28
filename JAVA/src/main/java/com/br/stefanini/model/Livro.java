package com.br.stefanini.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idlivro")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idcategoria")
	private Categoria categoria;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ideditora")
	private Editora editora;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "prefacio")
	private String prefacio;

	@Column(name = "autor")
	private String autor;

	@Column(name = "datapublicacao")
	private Date dataPublicacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPrefacio() {
		return prefacio;
	}

	public void setPrefacio(String prefacio) {
		this.prefacio = prefacio;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	

}
