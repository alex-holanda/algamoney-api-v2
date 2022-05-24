package com.algamoney.algamoney.lancamento.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoney.lancamento.domain.LancamentoService;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "lancamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class LancamentoController {

	private final LancamentoService lancamentoService;
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> listar() {
		return ResponseEntity.ok(lancamentoService.listar());
	}
}
