package com.eduardo.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.eduardo.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  // RETORNA O USUARIO QUE TIVER LOGADOR
	}catch(Exception e) {
	  return null;	
	}
  }
	
}
