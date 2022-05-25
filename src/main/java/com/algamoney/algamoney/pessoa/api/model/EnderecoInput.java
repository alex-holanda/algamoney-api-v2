package com.algamoney.algamoney.pessoa.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	private String complemento;

	@NotBlank
	private String bairro;

	@NotBlank
	private String cidade;

	@NotBlank
	private String estado;
}
