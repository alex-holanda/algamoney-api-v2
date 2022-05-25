package com.algamoney.algamoney.lancamento.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.lancamento.api.model.LancamentoInput;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LancamentoInputAssembler {

	private final ModelMapper modelMapper;
	
	public Lancamento toDomainObject(LancamentoInput lancamentoInput) {
		var lancamento = modelMapper.map(lancamentoInput, Lancamento.class);
		
		return lancamento;
	}
}
