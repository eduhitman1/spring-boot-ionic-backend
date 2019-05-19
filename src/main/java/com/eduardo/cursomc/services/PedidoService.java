package com.eduardo.cursomc.services;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.ItemPedido;
import com.eduardo.cursomc.domain.PagamentoComBoleto;
import com.eduardo.cursomc.domain.Pedido;
import com.eduardo.cursomc.domain.enums.EstadoPagamento;
import com.eduardo.cursomc.repositories.ItemPedidoRepository;
import com.eduardo.cursomc.repositories.PagamentoRepository;
import com.eduardo.cursomc.repositories.PedidoRepository;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
    @Autowired                           // VAI SER INSTANCIA AUTOMATICAMENTO PELO SPRING
    private PedidoRepository repo;    // DEPENDECIA DE OBJETO
	
    @Autowired
    private BoletoService boletoService;
    
    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired   
    private ProdutoService produtoService;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    
    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+",Tipo: "+ Pedido.class.getName()));
        
  }
     
    @Transactional   
    public Pedido insert(Pedido obj){
       obj.setId(null);  // SETAGEM DO OBJ PARA NULL
       obj.setInstante(new Date());
       obj.setCliente(clienteService.find(obj.getCliente().getId()));
       obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);   //INSERCAO PARA PENDENTE
       obj.getPagamento().setPedido(obj);
       
       if(obj.getPagamento() instanceof PagamentoComBoleto) {   //NUMERO DE PARCELAS
    	   PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
    	   boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());  // PREEMCHIMENTO DA DATA DE VENCIMENTO
       }
      obj = repo.save(obj); // SALVANDO PEDIDO
      pagamentoRepository.save(obj.getPagamento());
      
      for(ItemPedido ip : obj.getItens()){
    	ip.setDesconto(0.0);  
    	ip.setProduto(produtoService.find(ip.getProduto().getId()));
    	ip.setPreco(ip.getProduto().getPreco());
        ip.setPedido(obj);
      }
      itemPedidoRepository.saveAll(obj.getItens());
      System.out.println(obj);
      return obj;
    }
    
    
    
    
    
}
