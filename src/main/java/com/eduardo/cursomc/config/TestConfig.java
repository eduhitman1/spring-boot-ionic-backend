package com.eduardo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.eduardo.cursomc.services.DBService;
import com.eduardo.cursomc.services.EmailService;
import com.eduardo.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean    // COMPONENTE DE SISTEMAS = MYSQL
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean   // COMPONENTE DE SISTEMAS = EMAIL
	public  EmailService emailService(){
		return new MockEmailService();      // RETURN DE NOVA INSTANCIA DE MOCKEMAIL
	}
	
	
	
}
