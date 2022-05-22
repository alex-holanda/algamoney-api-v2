package com.algamoney.algamoney.categoria.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.categoria.api.controller.CategoriaController;
import com.algamoney.algamoney.categoria.api.model.CategoriaModel;
import com.algamoney.algamoney.categoria.domain.model.Categoria;

@Component
public class CategoriaModelAssembler extends RepresentationModelAssemblerSupport<Categoria, CategoriaModel> {

	private final ModelMapper modelMapper;

	public CategoriaModelAssembler(ModelMapper modelMapper) {
		super(CategoriaController.class, CategoriaModel.class);

		this.modelMapper = modelMapper;
	}

	@Override
	public CategoriaModel toModel(Categoria categoria) {
		var categoriaModel = createModelWithId(categoria.getId(), categoria);

		modelMapper.map(categoria, categoriaModel);

		return categoriaModel;
	}
	
	@Override
	public CollectionModel<CategoriaModel> toCollectionModel(Iterable<? extends Categoria> entities) {
		return super.toCollectionModel(entities);
	}
}
