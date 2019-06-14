package com.eduardo.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{   //CAMADA DE ACESSO A DADO (REPOSITORY)

	
	
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
	
}
