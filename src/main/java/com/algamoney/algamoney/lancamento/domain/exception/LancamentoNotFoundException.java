package com.algamoney.algamoney.lancamento.domain.exception;

import java.util.UUID;

import com.algamoney.algamoney.common.domain.EntityNotFoundException;

public class LancamentoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public LancamentoNotFoundException(UUID lancamentoId) {
		super(String.format("Não existe um lançamento com o código %s", lancamentoId));
	}

}
