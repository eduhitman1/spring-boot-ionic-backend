package com.eduardo.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.domain.Produto;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 =  new Produto(null , "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));      // CAT01 ESTÁ ASSOCIADO COM OS TRÊS PRODUTOS
		cat2.getProdutos().addAll(Arrays.asList(p2));            // CAT02 ESTÁ ASSOCIADO COM UM PRODUTO.
		
	    p1.getCategorias().addAll(Arrays.asList(cat1));        // PRODUTO 01 ESTÁ ASSOCIADO COM CATEGORIA 01
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));        // PRODUTO 02 ESTÁ ASSOCIADO COM CATEGORIA 01  e 02
		p3.getCategorias().addAll(Arrays.asList(cat1));        // PRODUTO 03 ESTÁ ASSOCIADO COM CATEGORIA 01
	
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		 
		
	}

	
}
