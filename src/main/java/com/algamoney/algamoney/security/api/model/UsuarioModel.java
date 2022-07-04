package com.algamoney.algamoney.security.api.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	
	private UUID id;
	
	private String nome;
	
	private String email;

}
