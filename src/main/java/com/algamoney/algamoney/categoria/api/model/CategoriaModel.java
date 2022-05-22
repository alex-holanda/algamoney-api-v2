package com.algamoney.algamoney.categoria.api.model;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "categorias")
public class CategoriaModel extends RepresentationModel<CategoriaModel> {

	private UUID id;
	private String nome;
}
