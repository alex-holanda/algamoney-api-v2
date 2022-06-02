package com.algamoney.algamoney.security.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.algamoney.algamoney.security.domain.model.Usuario;

import lombok.Getter;

@Getter
public class AuthorizationUser extends User {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	private String fullName;
	
	public AuthorizationUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		
		this.userId = usuario.getId().toString();
		this.fullName = usuario.getNome();
	}


}
