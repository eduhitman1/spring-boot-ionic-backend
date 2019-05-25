package com.eduardo.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims; //ARMAZENA AS REINVIDICAÇÕES DO TOKEN
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
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);              //ARMAZENA AS REINVIDICAÇÕES DO TOKEN
	    if(claims != null ) {
	    	String username = claims.getSubject();
	    	Date expirationDate = claims.getExpiration();   // 60 segundos
	    	Date now = new Date(System.currentTimeMillis());
	    	if(username != null && expirationDate != null && now.before(expirationDate) ) {
	    		return true;
	    	}
	    }
	    return false;
	}
	
	public String  getUsername(String token) {
		Claims claims = getClaims(token);              //ARMAZENA AS REINVIDICAÇÕES DO TOKEN
	    if(claims != null ) {
	    	return claims.getSubject();
	    }
	    return null;
	}
	
	

	private Claims getClaims(String token) {
		try {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();   //RECUPERA OS CLAIMS APARTI DO TOKEN
	}catch(Exception e) {
		return null;
	}
		
	}
	
	
	
}
