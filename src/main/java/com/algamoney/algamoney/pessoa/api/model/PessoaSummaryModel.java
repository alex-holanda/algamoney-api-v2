package com.algamoney.algamoney.pessoa.api.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaSummaryModel {
	private UUID id;

	private String nome;

	private Boolean ativo;
}
