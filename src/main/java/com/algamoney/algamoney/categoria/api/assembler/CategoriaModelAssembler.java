package com.algamoney.algamoney.categoria.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.categoria.api.model.CategoriaModel;
import com.algamoney.algamoney.categoria.domain.model.Categoria;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CategoriaModelAssembler {

	private final ModelMapper modelMapper;

	
	public CategoriaModel toModel(Categoria categoria) {
		var categoriaModel = modelMapper.map(categoria, CategoriaModel.class);
		
		return categoriaModel;
	}
	
	public List<CategoriaModel> toCollectionModel(List<Categoria> categorias) {
		return categorias.stream().map(this::toModel).collect(Collectors.toList());
	}
}
