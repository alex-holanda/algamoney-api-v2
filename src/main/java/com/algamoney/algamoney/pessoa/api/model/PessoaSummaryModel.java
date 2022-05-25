package com.algamoney.algamoney.pessoa.api.model;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaSummaryModel extends RepresentationModel<PessoaSummaryModel> {
	private UUID id;

	private String nome;

	private Boolean ativo;
}
