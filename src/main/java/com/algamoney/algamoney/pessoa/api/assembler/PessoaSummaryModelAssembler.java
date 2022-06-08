package com.algamoney.algamoney.pessoa.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.pessoa.api.model.PessoaSummaryModel;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PessoaSummaryModelAssembler {

	private final ModelMapper modelMapper;

	public PessoaSummaryModel toModel(Pessoa pessoa) {
		var pessoaModel = modelMapper.map(pessoa, PessoaSummaryModel.class);

		return pessoaModel;
	}
	
	public List<PessoaSummaryModel> toCollectionModel(List<Pessoa> pessoas) {
		return pessoas.stream().map(this::toModel).collect(Collectors.toList());
	}
}
