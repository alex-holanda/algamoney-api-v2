package com.algamoney.algamoney.categoria.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaInput {

	@NotBlank
	private String nome;
}
