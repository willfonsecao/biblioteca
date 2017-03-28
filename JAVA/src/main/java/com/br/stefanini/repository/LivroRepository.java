package com.br.stefanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.stefanini.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}
