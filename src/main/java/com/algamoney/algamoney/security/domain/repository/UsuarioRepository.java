package com.algamoney.algamoney.security.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.algamoney.algamoney.common.jpa.CustomJpaRepository;
import com.algamoney.algamoney.security.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, UUID> {

	Optional<Usuario> findByEmail(String email);
}
