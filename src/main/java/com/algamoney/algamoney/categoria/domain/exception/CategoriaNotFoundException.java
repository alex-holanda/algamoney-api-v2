package com.algamoney.algamoney.categoria.domain.exception;

import java.util.UUID;

import com.algamoney.algamoney.common.domain.EntityNotFoundException;

public class CategoriaNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public CategoriaNotFoundException(UUID categoriaId) {
		super(String.format("Não existe uma categoria com o código %s", categoriaId));
	}

}
