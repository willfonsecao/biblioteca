package com.br.stefanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.stefanini.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

	@Query("SELECT l FROM Livro l "
			+ "JOIN FETCH l.categoria c "
			+ "JOIN FETCH l.editora e ")
	List<Livro> buscarTodos();
}
