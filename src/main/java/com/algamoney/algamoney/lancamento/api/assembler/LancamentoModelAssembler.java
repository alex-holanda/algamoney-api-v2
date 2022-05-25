package com.algamoney.algamoney.lancamento.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.common.api.AlgaLinks;
import com.algamoney.algamoney.lancamento.api.controller.LancamentoController;
import com.algamoney.algamoney.lancamento.api.model.LancamentoModel;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;

@Component
public class LancamentoModelAssembler extends RepresentationModelAssemblerSupport<Lancamento, LancamentoModel> {

	private final ModelMapper modelMapper;
	
	private final AlgaLinks algalinks;
	
	public LancamentoModelAssembler(ModelMapper modelMapper, AlgaLinks algalinks) {
		super(LancamentoController.class, LancamentoModel.class);
		this.modelMapper = modelMapper;
		this.algalinks = algalinks;
	}

	@Override
	public LancamentoModel toModel(Lancamento lancamento) {
		var lancamentoModel = createModelWithId(lancamento.getId(), lancamento);
		
		modelMapper.map(lancamento, lancamentoModel);
		
		lancamentoModel.add(algalinks.linkToLancamentos("lancamentos"));
		
		
		lancamentoModel.getCategoria().add(algalinks.linkToCategoria(lancamento.getCategoria().getId(), "categoria"));
		lancamentoModel.getPessoa().add(algalinks.linkToPessoa(lancamento.getPessoa().getId(), "pessoa"));
		
		return lancamentoModel;
	}

}
