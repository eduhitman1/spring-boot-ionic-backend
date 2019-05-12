package com.eduardo.cursomc.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.cursomc.domain.Cidade;
import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.domain.Endereco;
import com.eduardo.cursomc.domain.enums.TipoCliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.dto.ClienteNewDTO;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.repositories.EnderecoRepository;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    @Autowired                           // VAI SER INSTANCIA AUTOMATICAMENTO PELO SPRING
    private ClienteRepository repo;    // DEPENDECIA DE OBJETO
	
   @Autowired 
   private EnderecoRepository enderecoRepository;
    
    
    public Cliente find(Integer id){
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+",Tipo: "+ Cliente.class.getName()));
  } 
    
    
  //METÓDO DE INSERÇÃO NO *ENDPOINT
    @Transactional 
    public Cliente insert(Cliente obj){
    obj.setId(null);
    obj = repo.save(obj);
    enderecoRepository.saveAll(obj.getEnderecos());
    return obj;
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
    	  throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");	
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
    
    // CLIENTEDTO
    public Cliente fromDTO(ClienteDTO objDto) {
       return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);	
    }
 // CLIENTENEWDTO
    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null , objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));	
     
        Cidade cid = new Cidade(objDto.getCidadeId(),null,null);
        Endereco end = new  Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
       cli.getEnderecos().add(end);
       cli.getTelefones().add(objDto.getTelefone1());
       if(objDto.getTelefone2()!= null) {
    	   cli.getTelefones().add(objDto.getTelefone2());
       }
       if(objDto.getTelefone3()!= null) {
    	   cli.getTelefones().add(objDto.getTelefone3());
       }
    
       return cli;
    
    }
    

    
   // VERSÃO 1.5..   
   //   public Cliente buscar(Integer id) {
   //   Cliente obj = repo.findOne(id);
   //    return obj;
   //   }
}
