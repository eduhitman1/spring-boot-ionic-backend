package com.eduardo.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.eduardo.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")    // PUXANDO VALOR DA CLASS AbstractEmailService// edu.hitman01.eh@gmail.com
	private String sender; 
	
	
	@Override   // SOBREESCREVENDO O METÓDO DA INTEFACE EmailService.Java
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {   //MÉTODO QUE PODE SER ACESSADO POR SUBCLASSES// não podera ser acesso pelo pacote de servico
		SimpleMailMessage sm = new SimpleMailMessage();
		 sm.setTo(obj.getCliente().getEmail());// DEFINIÇÃO BASE DE ALGUNS ATRIBUTOS DE EMAIL
		  sm.setFrom(sender);// EMAIL REMETENTE    default.sender=edu.hitman01.eh@gmail.com
		  sm.setSubject("Pedido Confirmado! Código: "+obj.getId()); // ASSUNTO
		  sm.setSentDate(new Date(System.currentTimeMillis()));// DATA DO EMAIL COM HORARIO DE SERVIDOR
		  sm.setText(obj.toString()); // COPO DO EMAIL
		 return sm;
	}
	
	
	
	
}
