package com.algamoney.algamoney.pessoa.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoney.pessoa.domain.PessoaService;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

	private final PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> listar() {
		return ResponseEntity.ok(pessoaService.listar());
	}
}
