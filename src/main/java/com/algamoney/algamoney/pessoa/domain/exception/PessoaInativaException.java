package com.algamoney.algamoney.pessoa.domain.exception;

import java.util.UUID;

import com.algamoney.algamoney.common.domain.BusinessException;

public class PessoaInativaException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public PessoaInativaException(UUID pessoaId) {
		super(String.format("A pessoa de código %s está inativa", pessoaId));
	}

}
