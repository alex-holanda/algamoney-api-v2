package com.algamoney.algamoney.lancamento.api.controller;

import com.algamoney.algamoney.common.api.util.ApiUtil;
import com.algamoney.algamoney.lancamento.api.assembler.LancamentoInputAssembler;
import com.algamoney.algamoney.lancamento.api.assembler.LancamentoModelAssembler;
import com.algamoney.algamoney.lancamento.api.model.LancamentoInput;
import com.algamoney.algamoney.lancamento.api.model.LancamentoModel;
import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.service.LancamentoService;
import com.algamoney.algamoney.security.CheckSecurity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "lancamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class LancamentoController {

	private final LancamentoService lancamentoService;

	private final LancamentoModelAssembler lancamentoModelAssembler;
	private final LancamentoInputAssembler lancamentoInputAssembler;

	@GetMapping
	@CheckSecurity.Lancamento.PodeConsultar
	public ResponseEntity<Page<LancamentoModel>> pesquisar(LancamentoFilter filter, Pageable pageable) {
		var lancamentosPage = lancamentoService.pesquisar(filter, pageable);
		var lancamentosModel = lancamentoModelAssembler.toCollectionModel(lancamentosPage.getContent());
		var lancamentosModelPage = new PageImpl<LancamentoModel>(lancamentosModel, pageable,
				lancamentosPage.getTotalElements());

		return ResponseEntity.ok(lancamentosModelPage);
	}

	@GetMapping("/{lancamentoId}")
	@CheckSecurity.Lancamento.PodeConsultar
	public ResponseEntity<LancamentoModel> buscar(@PathVariable UUID lancamentoId) {
		var lancamento = lancamentoService.buscar(lancamentoId);
		var lancamentoModel = lancamentoModelAssembler.toModel(lancamento);

		return ResponseEntity.ok(lancamentoModel);
	}

	@PostMapping
	@CheckSecurity.Lancamento.PodeGerenciar
	public ResponseEntity<LancamentoModel> adicionar(@RequestBody @Valid LancamentoInput lancamentoInput) {
		var lancamento = lancamentoInputAssembler.toDomainObject(lancamentoInput);

		lancamento = lancamentoService.adicionar(lancamento);

		var lancamentoModel = lancamentoModelAssembler.toModel(lancamento);

		var uri = ApiUtil.uri(lancamentoModel.getId());

		return ResponseEntity.created(uri).body(lancamentoModel);
	}

	@PutMapping("/{lancamentoId}")
	@CheckSecurity.Lancamento.PodeGerenciar
	public ResponseEntity<LancamentoModel> atualizar(@PathVariable UUID lancamentoId,
			@RequestBody @Valid LancamentoInput lancamentoInput) {
		var lancamento = lancamentoInputAssembler.toDomainObject(lancamentoInput);
		lancamento = lancamentoService.atualizar(lancamentoId, lancamento);

		var lancamentoModel = lancamentoModelAssembler.toModel(lancamento);

		return ResponseEntity.ok(lancamentoModel);
	}

	@DeleteMapping("/{lancamentoId}")
	@CheckSecurity.Lancamento.PodeGerenciar
	public ResponseEntity<Void> remover(@PathVariable UUID lancamentoId) {
		lancamentoService.remover(lancamentoId);

		return ResponseEntity.noContent().build();
	}
}
