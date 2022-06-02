package com.algamoney.algamoney.security.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algamoney.algamoney.security.domain.model.Usuario;
import com.algamoney.algamoney.security.domain.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		var usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		return new AuthorizationUser(usuario, getAuthorities(usuario));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
		return usuario.getGrupos().stream()
				.flatMap(grupo -> grupo.getPermissoes().stream()
						.map(permissao -> new SimpleGrantedAuthority(permissao.getNome().toUpperCase())))
				.collect(Collectors.toSet());
	}

}
