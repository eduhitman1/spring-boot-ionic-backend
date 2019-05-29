package com.eduardo.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.domain.Pedido;

public interface EmailService {

	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
	
	
	void sendOrderConfirmationEmail(Pedido obj);    // METODO DO AbstractEmailService
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);  // METODO DE EMAIL HTML
	void sendHtmlEmail(MimeMessage msg);

	
	
	
}
