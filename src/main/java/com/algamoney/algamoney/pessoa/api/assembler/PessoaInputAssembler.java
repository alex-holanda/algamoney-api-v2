package com.algamoney.algamoney.pessoa.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.pessoa.api.model.PessoaInput;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PessoaInputAssembler {

	private final ModelMapper modelMapper;
	
	public Pessoa toDomainObject(PessoaInput pessoaInput) {
		var pessoa = modelMapper.map(pessoaInput, Pessoa.class);
		
		return pessoa;
	}
}
