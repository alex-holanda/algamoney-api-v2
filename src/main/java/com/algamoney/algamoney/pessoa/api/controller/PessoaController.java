package com.algamoney.algamoney.pessoa.api.controller;

import com.algamoney.algamoney.common.api.util.ApiUtil;
import com.algamoney.algamoney.pessoa.api.assembler.PessoaInputAssembler;
import com.algamoney.algamoney.pessoa.api.assembler.PessoaModelAssembler;
import com.algamoney.algamoney.pessoa.api.model.PessoaInput;
import com.algamoney.algamoney.pessoa.api.model.PessoaModel;
import com.algamoney.algamoney.pessoa.domain.filter.PessoaFilter;
import com.algamoney.algamoney.pessoa.domain.service.PessoaService;
import com.algamoney.algamoney.security.CheckSecurity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

	private final PessoaService pessoaService;
	
	private final PessoaModelAssembler pessoaModelAssembler;
	private final PessoaInputAssembler pessoaInputAssembler;
	
	@GetMapping
	@CheckSecurity.Pessoa.PodeConsultar
	public ResponseEntity<List<PessoaModel>> pesquisar(PessoaFilter pessoaFilter) {
		var pessoas = pessoaService.listar(pessoaFilter);
		var pessoasModel = pessoaModelAssembler.toCollectionModel(pessoas);
		
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
