package com.algamoney.algamoney.security;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.security.domain.model.Usuario;
import com.algamoney.algamoney.security.domain.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AlgaSecurity {

	public static final String SCOPE_WRITE = "SCOPE_write";
	public static final String SCOPE_READ = "SCOPE_read";
	
	private final UsuarioRepository usuarioRepository;

//	Lançamentos
	
	public boolean podeConsultarLancamentos() {
		return isAuthenticated() && hasScopeRead();
	}
	
	public boolean podeGerenciarLancamento() {
		return isAuthenticated() && hasScopeWrite() && hasAuthority("EDITAR_LANCAMENTO");
	}
	
	public boolean podeGerarRelatorio() {
		return isAuthenticated() && hasScopeRead() && hasAuthority("GERAR_RELATORIOS");
	}
	
//	Pessoas
	
	public boolean podeConsultarPessoas() {
		return isAuthenticated() && hasScopeRead();
	}
	
	public boolean podeGerenciarPessoa() {
		return isAuthenticated() && hasScopeWrite() && hasAuthority("EDITAR_PESSOA");
	}
	
//	Categorias
	
	public boolean podeConsultarCategorias() {
		return isAuthenticated() && hasScopeRead();
	}
	
	public boolean podeGerenciarCategoria() {
		return isAuthenticated() && hasScopeWrite() && hasAuthority("EDITAR_CATEGORIA");
	}
	
//	escopos
	
	public boolean hasScopeRead() {
		return hasAuthority(SCOPE_READ);
	}
	
	public boolean hasScopeWrite() {
		return hasAnyAuthority(SCOPE_WRITE);
	}
	

//	getAuthenticated
	
	public Usuario getAuthenticatedUserOrfail() {
		return getAuthenticatedUser().orElseThrow(() -> new AccessDeniedException("Usuárion não encontrado"));
	}
	
	public Optional<Usuario> getAuthenticatedUser() {
		var userId = getUserId();
		
		if (userId == null) {
			return Optional.empty();
		}
		
		return usuarioRepository.findById(userId);
	}
	
	public boolean isAuthenticated() {
		return getAuthentication().isAuthenticated();
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean hasAnyAuthority(String... authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> Arrays.asList(authorityName).contains(authority.getAuthority()));
	}
	
	public UUID getUserId() {
		var jwt = getJwtToken();
		
		if (jwt == null) {
			return null;
		}
		
		return jwt.getClaim("user:id");
	}
	
	protected Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	private Jwt getJwtToken() {
		if (getAuthentication() != null && getAuthentication().getPrincipal() instanceof Jwt) {
			return (Jwt) getAuthentication().getPrincipal();
		}
		
		return null;
	}
}
