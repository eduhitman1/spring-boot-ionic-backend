package com.eduardo.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	
	@Value("${jwt.secret}")    //PASSAGEM DA PALAVRA SECRET E EXPIRATION (application.properts)
	private String secret;
	
	@Value("${jwt.expiration}")    //PASSAGEM DA PALAVRA SECRET E EXPIRATION (application.properts)
	private Long expiration;
	
	public String generateToken(String username) {
		return Jwts.builder()    // COMPONENTE VINDO DO POM JWT 
	      .setSubject(username)
	      .setExpiration(new Date(System.currentTimeMillis()+ expiration))   //TEMPO BASEADO NO TEMPO DE EXPIRAÇÃO, HORARIO ATUAL DO SISTEM + EXPIRATION
	      .signWith(SignatureAlgorithm.HS512, secret.getBytes())          //ALGORITMO DE TOKEM SECRET
	      .compact();
	}
}
