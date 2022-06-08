package com.algamoney.algamoney.pessoa.api.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaModel {
	private UUID id;

	private String nome;

	private Boolean ativo;
	
	private EnderecoModel endereco;

}
