package com.algamoney.algamoney.pessoa.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.common.api.AlgaLinks;
import com.algamoney.algamoney.pessoa.api.controller.PessoaController;
import com.algamoney.algamoney.pessoa.api.model.PessoaSummaryModel;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

@Component
public class PessoaSummaryModelAssembler extends RepresentationModelAssemblerSupport<Pessoa, PessoaSummaryModel> {

	private final ModelMapper modelMapper;
	
	private final AlgaLinks algalinks;

	public PessoaSummaryModelAssembler(ModelMapper modelMapper, AlgaLinks algalinks) {
		super(PessoaController.class, PessoaSummaryModel.class);
		this.modelMapper = modelMapper;
		this.algalinks = algalinks;
	}

	@Override
	public PessoaSummaryModel toModel(Pessoa pessoa) {
		var pessoaModel = createModelWithId(pessoa.getId(), pessoa);
		
		modelMapper.map(pessoa, pessoaModel);

		pessoaModel.add(algalinks.linkToPessoas("pessoas"));
		
		return pessoaModel;
	}
}
