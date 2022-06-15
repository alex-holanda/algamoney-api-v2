package com.algamoney.algamoney.pessoa.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.pessoa.api.model.PessoaModel;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PessoaModelAssembler {

	private final ModelMapper modelMapper;
	
	public PessoaModel toModel(Pessoa pessoa) {
		var pessoaModel = modelMapper.map(pessoa, PessoaModel.class);

		return pessoaModel;
	}
	
	public List<PessoaModel> toCollectionModel(List<Pessoa> pessoas) {
		return pessoas.stream().map(this::toModel).collect(Collectors.toList());
	}
}
