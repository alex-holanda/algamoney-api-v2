package com.algamoney.algamoney.pessoa.domain.exception;

import java.util.UUID;

import com.algamoney.algamoney.common.domain.EntityInUseException;

public class PessoaInUseException extends EntityInUseException {

	private static final long serialVersionUID = 1L;

	public PessoaInUseException(UUID pessoaId) {
		super(String.format("Pessoa de código %s não pode ser removida, pois está em uso", pessoaId));
	}
}
