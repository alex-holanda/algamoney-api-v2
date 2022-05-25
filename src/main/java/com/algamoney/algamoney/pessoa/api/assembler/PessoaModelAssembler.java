package com.algamoney.algamoney.pessoa.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.common.api.AlgaLinks;
import com.algamoney.algamoney.pessoa.api.controller.PessoaController;
import com.algamoney.algamoney.pessoa.api.model.PessoaModel;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

@Component
public class PessoaModelAssembler extends RepresentationModelAssemblerSupport<Pessoa, PessoaModel> {

	private final ModelMapper modelMapper;
	
	private final AlgaLinks algalinks;

	public PessoaModelAssembler(ModelMapper modelMapper, AlgaLinks algalinks) {
		super(PessoaController.class, PessoaModel.class);
		this.modelMapper = modelMapper;
		this.algalinks = algalinks;
	}

	@Override
	public PessoaModel toModel(Pessoa pessoa) {
		var pessoaModel = createModelWithId(pessoa.getId(), pessoa);
		
		modelMapper.map(pessoa, pessoaModel);

		pessoaModel.add(algalinks.linkToPessoas("pessoas"));
		
		return pessoaModel;
	}
}
