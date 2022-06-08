package com.algamoney.algamoney.pessoa.api.assembler;

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
}
