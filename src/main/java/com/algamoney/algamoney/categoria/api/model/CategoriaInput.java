package com.algamoney.algamoney.categoria.api.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaInput {

	@NotBlank
	private String nome;
}
