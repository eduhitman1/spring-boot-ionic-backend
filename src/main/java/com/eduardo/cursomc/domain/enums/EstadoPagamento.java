package com.eduardo.cursomc.domain.enums;

public enum EstadoPagamento {
   
	PENDENTE(1,"pendente"),
	QUITADO(2,"quitado"),
	CANCELADO(3,"cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
		
		
		public int getCod() {
			return cod;
	}
		
		public String getDescricao() {
			return descricao;
		}
		
		public static EstadoPagamento toEnum(Integer cod) {
			
			if(cod == null) {
				return null;
			}
        
			for(EstadoPagamento x : EstadoPagamento.values()) {
				if(cod.equals(x.getCod())) {                     // está percorrendo a linha 5,6
					return x;
				}  
			}
			
			
		throw new IllegalArgumentException("Id inválido: "+cod);
		}
	
	
	
	
}
