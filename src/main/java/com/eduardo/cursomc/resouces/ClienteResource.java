package com.eduardo.cursomc.resouces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)           // PASSANDO O ID DA CATEGORIA
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {        // pathvariable é para identificar o id value 
		
		
		Cliente obj = service.find(id);                            // BUSCANDO O METÓDO NA CLASS categoriaService 
		return ResponseEntity.ok().body(obj);                        // CORPO DO OBJETO	 
		
	
	                                                                //	Cliente cat1 = new Cliente(1, "Informatica");
	                                                                //	Cliente cat2 = new Cliente(2, "Escritório");
	                                                               //	List<Cliente> lista = new ArrayList<>();
	                                                               //	lista.add(cat1);
	                                                              //	lista.add(cat2);
	                                                                //	return lista;
		
		
	}
}
