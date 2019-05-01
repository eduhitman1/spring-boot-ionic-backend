package com.eduardo.cursomc.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired                           // VAI SER INSTANCIA AUTOMATICAMENTO PELO SPRING
    private CategoriaRepository repo;    // DEPENDECIA DE OBJETO
	
    public Categoria find(Integer id){
        Optional<Categoria> obj = repo.findById(id);
        
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+",Tipo: "+ Categoria.class.getName()));
        
  }

    
   // VERSÃO 1.5..   
   //   public Categoria buscar(Integer id) {
   //   Categoria obj = repo.findOne(id);
   //    return obj;
   //   }
}
