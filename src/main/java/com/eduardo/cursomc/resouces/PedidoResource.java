package com.eduardo.cursomc.resouces;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardo.cursomc.domain.Pedido;
import com.eduardo.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	//METODO DE INSERÇÃO NO *ENDPOINT
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void>  insert(@Valid @RequestBody  Pedido obj){
		obj = service.insert(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	    		.path("/{id}").buildAndExpand(obj.getId()).toUri();
	    
	return ResponseEntity.created(uri).build();
	}

@RequestMapping(value="/{id}",method=RequestMethod.GET)           // PASSANDO O ID DA CATEGORIA
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {        // pathvariable é para identificar o id value 
		Pedido obj = service.find(id);                            // BUSCANDO O METÓDO NA CLASS categoriaService 
		return ResponseEntity.ok().body(obj);                        // CORPO DO OBJETO	 
	}
}
