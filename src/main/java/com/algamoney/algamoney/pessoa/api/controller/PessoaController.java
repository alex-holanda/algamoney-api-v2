package com.algamoney.algamoney.pessoa.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoney.common.api.util.ApiUtil;
import com.algamoney.algamoney.pessoa.api.assembler.PessoaInputAssembler;
import com.algamoney.algamoney.pessoa.api.assembler.PessoaModelAssembler;
import com.algamoney.algamoney.pessoa.api.assembler.PessoaSummaryModelAssembler;
import com.algamoney.algamoney.pessoa.api.model.PessoaInput;
import com.algamoney.algamoney.pessoa.api.model.PessoaModel;
import com.algamoney.algamoney.pessoa.api.model.PessoaSummaryModel;
import com.algamoney.algamoney.pessoa.domain.service.PessoaService;
import com.algamoney.algamoney.security.CheckSecurity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

	private final PessoaService pessoaService;
	
	private final PessoaModelAssembler pessoaModelAssembler;
	private final PessoaSummaryModelAssembler pessoaSummaryModelAssembler;
	private final PessoaInputAssembler pessoaInputAssembler;
	
	@GetMapping
	@CheckSecurity.Pessoa.PodeConsultar
	public ResponseEntity<CollectionModel<PessoaSummaryModel>> listar() {
		var pessoas = pessoaService.listar();
		var pessoasModel = pessoaSummaryModelAssembler.toCollectionModel(pessoas);
		
		return ResponseEntity.ok(pessoasModel);
	}
	
	@GetMapping("/{pessoaId}")
	@CheckSecurity.Pessoa.PodeConsultar
	public ResponseEntity<PessoaModel> buscar(@PathVariable UUID pessoaId) {
		var pessoa = pessoaService.buscar(pessoaId);
		var pessoaModel = pessoaModelAssembler.toModel(pessoa);
		
		return ResponseEntity.ok(pessoaModel);
	}
	
	@PostMapping
	@CheckSecurity.Pessoa.PodeGerenciar
	public ResponseEntity<PessoaModel> adicionar(@RequestBody @Valid PessoaInput pessoaInput) {
		var pessoa = pessoaInputAssembler.toDomainObject(pessoaInput);
		pessoa = pessoaService.adicionar(pessoa);
		
		var pessoaModel = pessoaModelAssembler.toModel(pessoa);

		var uri = ApiUtil.uri(pessoaModel.getId());
		
		return ResponseEntity.created(uri).body(pessoaModel);
	}
	
	@PutMapping("/{pessoaId}")
	@CheckSecurity.Pessoa.PodeGerenciar
	public ResponseEntity<PessoaModel> atualizar(@PathVariable UUID pessoaId, @RequestBody @Valid PessoaInput pessoaInput) {
		var pessoa = pessoaInputAssembler.toDomainObject(pessoaInput);
		pessoa = pessoaService.atualizar(pessoaId, pessoa);
		
		var pessoaModel = pessoaModelAssembler.toModel(pessoa);
		
		return ResponseEntity.ok(pessoaModel);
	}
	
	@PutMapping("/{pessoaId}/ativo")
	@CheckSecurity.Pessoa.PodeGerenciar
	public ResponseEntity<Void> ativar(@PathVariable UUID pessoaId) {
		pessoaService.ativar(pessoaId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{pessoaId}")
	@CheckSecurity.Pessoa.PodeGerenciar
	public ResponseEntity<Void> remover(@PathVariable UUID pessoaId) {
		pessoaService.remover(pessoaId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{pessoaId}/ativo")
	@CheckSecurity.Pessoa.PodeGerenciar
	public ResponseEntity<Void> inativar(@PathVariable UUID pessoaId) {
		pessoaService.inativar(pessoaId);
		
		return ResponseEntity.noContent().build();
	}
}
