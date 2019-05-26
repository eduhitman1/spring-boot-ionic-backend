package com.eduardo.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eduardo.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails{
    private static final long serialVersionUID = 1L;
	
    private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorites;
	
	public UserSS(){
	}
	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorites = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());  // PUXANDO TODO OS TIPO DE PERFIS
	}



	public Integer getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(Perfil perfil) {          //TESTE SE POSSUI UM DADOS PERFIL
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
	
	
}
