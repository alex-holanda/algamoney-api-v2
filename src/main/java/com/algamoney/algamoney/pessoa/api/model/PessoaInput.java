package com.algamoney.algamoney.pessoa.api.model;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
