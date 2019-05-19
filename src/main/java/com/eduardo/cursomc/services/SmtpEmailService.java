package com.eduardo.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{

	 @Autowired // EMAIL SENDER
	 private MailSender mailSender;  //INSTANCIA DE TODOS DADOS DO application-dev.propeties PART EMAIL 
	
	
	 private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);   //static para correspondencia de todo mundo

	 
	 
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);
		LOG.info("Email enviado");
		
	}

}
