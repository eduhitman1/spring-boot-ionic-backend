package com.eduardo.cursomc.resouces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.domain.Pedido;
import com.eduardo.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)           // PASSANDO O ID DA CATEGORIA
	public ResponseEntity<?> find(@PathVariable Integer id) {        // pathvariable é para identificar o id value 
		
		
		Pedido obj = service.find(id);                            // BUSCANDO O METÓDO NA CLASS categoriaService 
		return ResponseEntity.ok().body(obj);                        // CORPO DO OBJETO	 
		
	
	                                                                //	Pedido cat1 = new Pedido(1, "Informatica");
	                                                                //	Pedido cat2 = new Pedido(2, "Escritório");
	                                                               //	List<Pedido> lista = new ArrayList<>();
	                                                               //	lista.add(cat1);
	                                                              //	lista.add(cat2);
	                                                                //	return lista;
		
		
	}
}
