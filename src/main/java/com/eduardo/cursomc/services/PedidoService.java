package com.eduardo.cursomc.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Pedido;
import com.eduardo.cursomc.repositories.PedidoRepository;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

import java.util.Optional;

@Service
public class PedidoService {
    @Autowired                           // VAI SER INSTANCIA AUTOMATICAMENTO PELO SPRING
    private PedidoRepository repo;    // DEPENDECIA DE OBJETO
	
    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+",Tipo: "+ Pedido.class.getName()));
        
  }

    
   // VERSÃO 1.5..   
   //   public Pedido buscar(Integer id) {
   //   Pedido obj = repo.findOne(id);
   //    return obj;
   //   }
}
