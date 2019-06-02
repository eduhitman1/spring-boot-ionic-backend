package com.eduardo.cursomc.resouces;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.dto.ClienteNewDTO;
import com.eduardo.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	// METODO DE INSERÇÃO NO *ENDPOINT
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// METODO DE UPDATE NO *ENDPOINT
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// METODO DE DELETE NO *ENDPOINT
	@PreAuthorize("hasAnyRole('ADMIN')") // SOMENTE ADMINISTRADOR
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // PASSANDO O ID DA CATEGORIA
	public ResponseEntity<Void> delete(@PathVariable Integer id) { // pathvariable é para identificar o id value
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	// METODO DE LISTAGEM NO *ENDPOINT
	@PreAuthorize("hasAnyRole('ADMIN')") // SOMENTE ADMINISTRADOR
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	// METODO DE LISTAGEM POR PAGINAÇÃO NO *ENDPOINT
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, // ordenação por nome
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) { // ordenação por crescente
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	// COD ESTAVA ACIMA DOS METODO CRUD
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // PASSANDO O ID DA CATEGORIA
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { // pathvariable é para identificar o id value

		Cliente obj = service.find(id); // BUSCANDO O METÓDO NA CLASS categoriaService
		return ResponseEntity.ok().body(obj); // CORPO DO OBJETO
												// Cliente cat1 = new Cliente(1, "Informatica");
												// Cliente cat2 = new Cliente(2, "Escritório");
												// List<Cliente> lista = new ArrayList<>();
												// lista.add(cat1);
												// lista.add(cat2);
												// return lista;

	}

	// END POINT DE PICTURE
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}

}
