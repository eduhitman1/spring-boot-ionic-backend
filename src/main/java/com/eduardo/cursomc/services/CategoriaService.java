package com.eduardo.cursomc.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

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
     
    //METÓDO DE DELETE
    public void delete(Integer id) {
    	find(id);
    	try{
    	repo.deleteById(id);
    	} catch(DataIntegrityViolationException e) {
    	  throw new DataIntegrityException("Nâo é possivel excluir uma categoria que possui produtos");	
    	}
    }
}
