package com.algamoney.algamoney.categoria.api.controller;

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

import com.algamoney.algamoney.categoria.api.assembler.CategoriaInputAssembler;
import com.algamoney.algamoney.categoria.api.assembler.CategoriaModelAssembler;
import com.algamoney.algamoney.categoria.api.model.CategoriaInput;
import com.algamoney.algamoney.categoria.api.model.CategoriaModel;
import com.algamoney.algamoney.categoria.domain.service.CategoriaService;
import com.algamoney.algamoney.common.api.util.ApiUtil;
import com.algamoney.algamoney.security.CheckSecurity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaController {

	private final CategoriaService categoriaService;

	private final CategoriaModelAssembler categoriaModelAssembler;
	private final CategoriaInputAssembler categoriaInputAssembler;

	@GetMapping
	@CheckSecurity.Categoria.PodeConsultar
	public ResponseEntity<CollectionModel<CategoriaModel>> listar() {
		var categorias = categoriaService.listar();
		var categoriasModel = categoriaModelAssembler.toCollectionModel(categorias);

		return ResponseEntity.ok(categoriasModel);
	}

	@GetMapping("/{categoriaId}")
	@CheckSecurity.Categoria.PodeConsultar
	public ResponseEntity<CategoriaModel> buscar(@PathVariable UUID categoriaId) {
		var categoria = categoriaService.buscar(categoriaId);
		var categoriaModel = categoriaModelAssembler.toModel(categoria);

		return ResponseEntity.ok(categoriaModel);
	}

	@PostMapping
	@CheckSecurity.Categoria.PodeGerenciar
	public ResponseEntity<CategoriaModel> adicionar(@Valid @RequestBody CategoriaInput categoriaInput) {
		var categoria = categoriaInputAssembler.toDomainObject(categoriaInput);
		categoria = categoriaService.adicionar(categoria);

		var categoriaModel = categoriaModelAssembler.toModel(categoria);

		var uri = ApiUtil.uri(categoriaModel.getId());

		return ResponseEntity.created(uri).body(categoriaModel);
	}

	@PutMapping("/{categoriaId}")
	@CheckSecurity.Categoria.PodeGerenciar
	public ResponseEntity<CategoriaModel> atualizar(@PathVariable UUID categoriaId,
			@Valid @RequestBody CategoriaInput categoriaInput) {

		var categoria = categoriaInputAssembler.toDomainObject(categoriaInput);
		categoria = categoriaService.atualizar(categoriaId, categoria);

		var categoriaModel = categoriaModelAssembler.toModel(categoria);

		return ResponseEntity.ok(categoriaModel);
	}

	@DeleteMapping("/{categoriaId}")
	@CheckSecurity.Categoria.PodeGerenciar
	public ResponseEntity<Void> remover(@PathVariable UUID categoriaId) {
		categoriaService.remover(categoriaId);
		return ResponseEntity.noContent().build();
	}
}
