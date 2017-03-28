package com.br.stefanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.stefanini.model.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long>{

}
