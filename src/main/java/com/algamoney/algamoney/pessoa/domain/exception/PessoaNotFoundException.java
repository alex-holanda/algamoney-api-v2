package com.algamoney.algamoney.pessoa.domain.exception;

import java.util.UUID;

import com.algamoney.algamoney.common.domain.EntityNotFoundException;

public class PessoaNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public PessoaNotFoundException(UUID pessoaId) {
		super(String.format("Não existe uma pessoa com o código %s", pessoaId));
	}
}
