package com.eduardo.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.security.UserSS;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ClienteRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Cliente cli = repo.findByEmail(email);
		if(cli == null) {   //SIGNIFICAR QUE ESSE EMAIL NÃO EXISTE
			throw new UsernameNotFoundException(email);  //INFORM DE EMAIL QUE NÃO EXISTIA
		}
		return new UserSS(cli.getId(),cli.getEmail(), cli.getSenha(), cli.getPerfis());   //PUXANDO DA CLASS USERSS
	}

}
