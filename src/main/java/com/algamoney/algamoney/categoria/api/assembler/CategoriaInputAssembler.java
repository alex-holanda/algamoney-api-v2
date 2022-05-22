package com.algamoney.algamoney.categoria.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.categoria.api.model.CategoriaInput;
import com.algamoney.algamoney.categoria.domain.model.Categoria;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CategoriaInputAssembler {
	
	private final ModelMapper modelMapper;
	
	public Categoria toDomainObject(CategoriaInput categoriaInput) {
		var categoria = modelMapper.map(categoriaInput, Categoria.class);
		
		return categoria;
	}
	
}
