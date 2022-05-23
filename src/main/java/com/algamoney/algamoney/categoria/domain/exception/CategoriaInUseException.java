package com.algamoney.algamoney.categoria.domain.exception;

import java.util.UUID;

import com.algamoney.algamoney.common.domain.EntityInUseException;

public class CategoriaInUseException extends EntityInUseException {

	private static final long serialVersionUID = 1L;

	public CategoriaInUseException(UUID categoriaId) {
		super(String.format("Categoria de código %s não pode ser removida, pois está em uso", categoriaId));
	}

}
