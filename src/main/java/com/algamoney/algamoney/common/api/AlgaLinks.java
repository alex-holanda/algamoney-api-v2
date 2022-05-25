package com.algamoney.algamoney.common.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.algamoney.algamoney.categoria.api.controller.CategoriaController;
import com.algamoney.algamoney.lancamento.api.controller.LancamentoController;
import com.algamoney.algamoney.pessoa.api.controller.PessoaController;

@Component
public class AlgaLinks {

	private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

//	Lancamento
	
	public Link linkToLancamentos(String rel) {
		var filtroVariables = new TemplateVariables(
				new TemplateVariable("tipo", VariableType.REQUEST_PARAM),
				new TemplateVariable("categoria", VariableType.REQUEST_PARAM),
				new TemplateVariable("pessoa", VariableType.REQUEST_PARAM),
				new TemplateVariable("vencimento", VariableType.REQUEST_PARAM));
		
		var lancamentosUrl = linkTo(LancamentoController.class).toUri().toString();
		
		return Link.of(UriTemplate.of(lancamentosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToLancamento() {
		return linkToLancamentos(IanaLinkRelations.SELF_VALUE);
	}
	
//	Categoria
	public Link linkToCategorias() {
		return linkToCategorias(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToCategorias(String rel) {
		return linkTo(CategoriaController.class).withRel(rel);
	}
	
	public Link linkToCategoria(UUID categoriaId, String rel) {
		return linkTo(methodOn(CategoriaController.class).buscar(categoriaId)).withRel(rel);
	}
	
	public Link linkToCategoria(UUID categoriaId) {
		return linkToCategoria(categoriaId, IanaLinkRelations.SELF_VALUE);
	}
	
//	Pessoa
	
	public Link linkToPessoas() {
		return linkToPessoas(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToPessoas(String rel) {
		return linkTo(PessoaController.class).withRel(rel);
	}
	
	public Link linkToPessoa(UUID pessoaId, String rel) {
		return linkTo(methodOn(PessoaController.class).buscar(pessoaId)).withRel(rel);
	}
	
	public Link linkToPessoa(UUID pessoaId) {
		return linkToCategoria(pessoaId, IanaLinkRelations.SELF_VALUE);
	}
}
