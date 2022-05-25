package com.algamoney.algamoney.categoria.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.categoria.api.controller.CategoriaController;
import com.algamoney.algamoney.categoria.api.model.CategoriaModel;
import com.algamoney.algamoney.categoria.domain.model.Categoria;
import com.algamoney.algamoney.common.api.AlgaLinks;

@Component
public class CategoriaModelAssembler extends RepresentationModelAssemblerSupport<Categoria, CategoriaModel> {

	private final ModelMapper modelMapper;

	private final AlgaLinks algalinks;
	
	public CategoriaModelAssembler(ModelMapper modelMapper, AlgaLinks algalinks) {
		super(CategoriaController.class, CategoriaModel.class);

		this.modelMapper = modelMapper;
		this.algalinks = algalinks;
	}

	@Override
	public CategoriaModel toModel(Categoria categoria) {
		var categoriaModel = createModelWithId(categoria.getId(), categoria);

		modelMapper.map(categoria, categoriaModel);
		
		categoriaModel.add(algalinks.linkToCategorias("categorias"));

		return categoriaModel;
	}
}
