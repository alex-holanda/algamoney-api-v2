package com.algamoney.algamoney.pessoa.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaInput {

	@NotBlank
	private String nome;

	@NotNull
	private Boolean ativo;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;
}
