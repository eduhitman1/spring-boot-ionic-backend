package com.eduardo.cursomc.resouces;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)           // PASSANDO O ID DA CATEGORIA
	public ResponseEntity<?> find(@PathVariable Integer id) {        // pathvariable é para identificar o id value 
		
		
		Categoria obj = service.find(id);                            // BUSCANDO O METÓDO NA CLASS categoriaService 
		return ResponseEntity.ok().body(obj);                        // CORPO DO OBJETO	 
		
	
	                                                                //	Categoria cat1 = new Categoria(1, "Informatica");
	                                                                //	Categoria cat2 = new Categoria(2, "Escritório");
	                                                               //	List<Categoria> lista = new ArrayList<>();
	                                                               //	lista.add(cat1);
	                                                              //	lista.add(cat2);
	                                                                //	return lista;
		
		
	}
}
