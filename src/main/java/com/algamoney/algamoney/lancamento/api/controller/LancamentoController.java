package com.algamoney.algamoney.lancamento.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import com.algamoney.algamoney.lancamento.api.assembler.LancamentoInputAssembler;
import com.algamoney.algamoney.lancamento.api.assembler.LancamentoModelAssembler;
import com.algamoney.algamoney.lancamento.api.model.LancamentoInput;
import com.algamoney.algamoney.lancamento.api.model.LancamentoModel;
import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.service.LancamentoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "lancamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class LancamentoController {

	private final LancamentoService lancamentoService;

	private final LancamentoModelAssembler lancamentoModelAssembler;
	private final PagedResourcesAssembler<Lancamento> pagedResourcesAssembler;
	private final LancamentoInputAssembler lancamentoInputAssembler;

	@GetMapping
	public ResponseEntity<PagedModel<LancamentoModel>> pesquisar(LancamentoFilter filter, Pageable pageable) {
		var lancamentosPage = lancamentoService.pesquisar(filter, pageable);
		var pagedModel = pagedResourcesAssembler.toModel(lancamentosPage, lancamentoModelAssembler);

		return ResponseEntity.ok(pagedModel);
	}
	
	@GetMapping("/{lancamentoId}")
	public ResponseEntity<LancamentoModel> buscar(@PathVariable UUID lancamentoId) {
		var lancamento = lancamentoService.buscar(lancamentoId);
		var lancamentoModel = lancamentoModelAssembler.toModel(lancamento);

		return ResponseEntity.ok(lancamentoModel);
	}

	@PostMapping
	public ResponseEntity<LancamentoModel> adicionar(@RequestBody @Valid LancamentoInput lancamentoInput) {
		var lancamento = lancamentoInputAssembler.toDomainObject(lancamentoInput);
		lancamento = lancamentoService.adicionar(lancamento);

		var lancamentoModel = lancamentoModelAssembler.toModel(lancamento);

		var uri = ApiUtil.uri(lancamentoModel.getId());

		return ResponseEntity.created(uri).body(lancamentoModel);
	}

	@PutMapping("/{lancamentoId}")
	public ResponseEntity<LancamentoModel> atualizar(@PathVariable UUID lancamentoId,
			@RequestBody @Valid LancamentoInput lancamentoInput) {
		var lancamento = lancamentoInputAssembler.toDomainObject(lancamentoInput);
		lancamento = lancamentoService.atualizar(lancamentoId, lancamento);

		var lancamentoModel = lancamentoModelAssembler.toModel(lancamento);

		return ResponseEntity.ok(lancamentoModel);
	}
	
	@DeleteMapping("/{lancamentoId}")
	public ResponseEntity<Void> remover(@PathVariable UUID lancamentoId) {
		lancamentoService.remover(lancamentoId);
		
		return ResponseEntity.noContent().build();
	}
}
