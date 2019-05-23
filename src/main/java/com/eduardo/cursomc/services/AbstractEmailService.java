package com.eduardo.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.eduardo.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")    // PUXANDO VALOR DA CLASS AbstractEmailService// edu.hitman01.eh@gmail.com
	private String sender; 
	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	
	@Autowired
	private JavaMailSender javaMailSender;      // INSTANCIO O MINIMESSAGE
	
	
	
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
	

	protected String htmlFromTemplatePedido(Pedido obj) {          //ENVIANDO PARA O TEMPLATES 
		Context context = new Context();                            
		context.setVariable("pedido",obj);               //CORRESPONDENCIA DO pedido COM O TEMPLATE
	    return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override                   // SOBREESCREVENDO O METÓDO DA INTEFACE EmailService.Java
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {        
	try {
		MimeMessage mm = prepareMimeMessageFromPedido(obj);
		sendHtmlEmail(mm);                  // NOVO CONTRATO DE EMAIL COM HTML
	}catch(MessagingException ex) {
		sendOrderConfirmationEmail(obj);         //CASO NÃO DER CERTO O EMAIL HTML
	}
	
	}

	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException { 
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());   // EMAIL DESTINATÁRIO
		mmh.setFrom(sender);               //EMAIL REMETENTE
		mmh.setSubject("Pedido confirmado! Código"+ obj.getId());   //ASSUNTO DO EMAIL
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj),true);
		return mimeMessage;
	}
}
