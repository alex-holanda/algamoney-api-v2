package com.algamoney.algamoney.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Categoria {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarCategorias")
		public @interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarCategoria")
		public @interface PodegGerenciar {}
	}
}
