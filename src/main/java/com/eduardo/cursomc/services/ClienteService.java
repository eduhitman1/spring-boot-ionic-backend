package com.eduardo.cursomc.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

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

    
   // VERSÃO 1.5..   
   //   public Cliente buscar(Integer id) {
   //   Cliente obj = repo.findOne(id);
   //    return obj;
   //   }
}
