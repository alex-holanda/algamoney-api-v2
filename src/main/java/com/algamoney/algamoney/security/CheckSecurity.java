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
		public @interface PodeConsultar {
		}

		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarCategoria")
		public @interface PodeGerenciar {
		}
	}

	public @interface Pessoa {

		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarPessoas")
		public @interface PodeConsultar {
		}

		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarPessoa")
		public @interface PodeGerenciar {
		}
	}

	public @interface Lancamento {

		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeConsultarLancamentos")
		public @interface PodeConsultar {
		}

		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerenciarLancamento")
		public @interface PodeGerenciar {
		}

		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("@algaSecurity.podeGerarRelatorio")
		public @interface PodeGerarRelatorio {
		}
	}
}
