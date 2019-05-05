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
   
    
    //METÓDO DE INSERÇÃO
    public Categoria insert(Categoria obj) {
    	obj.setId(null);
    	return repo.save(obj);     // RESPONSE SAVE OBJ
    }
    
    //METÓDO DE UPDATE
    public Categoria update(Categoria obj) {
    	find(obj.getId());         // ESTÁ PUCHANDO O METÓDO ACIMA FIND
    	return repo.save(obj);     // RESPONSE UPDATE OBJ
    }
    

    
   // VERSÃO 1.5..   
   //   public Categoria buscar(Integer id) {
   //   Categoria obj = repo.findOne(id);
   //    return obj;
   //   }
}
