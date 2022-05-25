package com.algamoney.algamoney.pessoa.api.model;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "pessoas")
public class PessoaModel extends RepresentationModel<PessoaModel> {
	private UUID id;

	private String nome;

	private Boolean ativo;
	
	private EnderecoModel endereco;

}
