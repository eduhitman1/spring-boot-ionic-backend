package com.eduardo.cursomc.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
   
   //METÓDO DE INSERÇÃO NO *ENDPOINT
    public Categoria insert(Categoria obj) {
    	obj.setId(null);
    	return repo.save(obj);     // RESPONSE SAVE OBJ
    }
    
    //METÓDO DE UPDATE  NO *ENDPOINT
    public Categoria update(Categoria obj) {
    	find(obj.getId());         // ESTÁ PUCHANDO O METÓDO ACIMA FIND
    	return repo.save(obj);     // RESPONSE UPDATE OBJ
    }
     
    //METÓDO DE DELETE NO *ENDPOINT
    public void delete(Integer id) {
    	find(id);
    	try{
    	repo.deleteById(id);
    	} catch(DataIntegrityViolationException e) {
    	  throw new DataIntegrityException("Nâo é possivel excluir uma categoria que possui produtos");	
    	}
    }
    
    //METÓDO DE LISTAGEM NO *ENDPOINT
    public List<Categoria> findAll(){
    	return repo.findAll();
    }
    //METÓDO DE LISTAGEM POR PAGINAÇÃO, NÃO PUXANDO TODA LISTAGEM
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        return repo.findAll(pageRequest);
    }
    
}
