package com.algamoney.algamoney.lancamento.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.lancamento.api.model.LancamentoModel;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LancamentoModelAssembler {

	private final ModelMapper modelMapper;
	
	public LancamentoModel toModel(Lancamento lancamento) {
		var lancamentoModel = modelMapper.map(lancamento, LancamentoModel.class);
		
		return lancamentoModel;
	}
	
	public List<LancamentoModel> toCollectionModel(List<Lancamento> lancamentos) {
		return lancamentos.stream().map(this::toModel).collect(Collectors.toList());
	}

}
