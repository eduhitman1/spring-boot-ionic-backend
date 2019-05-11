package com.eduardo.cursomc.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired                           // VAI SER INSTANCIA AUTOMATICAMENTO PELO SPRING
    private ClienteRepository repo;    // DEPENDECIA DE OBJETO
	
    public Cliente find(Integer id){
        Optional<Cliente> obj = repo.findById(id);
        
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+",Tipo: "+ Cliente.class.getName()));
        
  }  
    
    //METÓDO DE UPDATE  NO *ENDPOINT
    public Cliente update(Cliente obj) {
    Cliente newObj = find(obj.getId());         // ESTÁ PUCHANDO O METÓDO ACIMA FIND
    updateData(newObj, obj);
    return repo.save(newObj);
    //return repo.save(obj);     // RESPONSE UPDATE OBJ
    }
     
    //METÓDO DE DELETE NO *ENDPOINT
    public void delete(Integer id) {
    	find(id);
    	try{
    	repo.deleteById(id);
    	} catch(DataIntegrityViolationException e) {
    	  throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");	
    	}
    }
    
    //METÓDO DE LISTAGEM NO *ENDPOINT
    public List<Cliente> findAll(){
    	return repo.findAll();
    }
    //METÓDO DE LISTAGEM POR PAGINAÇÃO, NÃO PUXANDO TODA LISTAGEM
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        return repo.findAll(pageRequest);
    }
    
    private void updateData(Cliente newObj , Cliente obj) {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    }
    
    
    
    public Cliente fromDTO(ClienteDTO objDto) {
       return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);	
    }
    

    
   // VERSÃO 1.5..   
   //   public Cliente buscar(Integer id) {
   //   Cliente obj = repo.findOne(id);
   //    return obj;
   //   }
}
