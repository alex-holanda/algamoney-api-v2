package com.algamoney.algamoney.categoria.api.controller;

import com.algamoney.algamoney.categoria.api.assembler.CategoriaInputAssembler;
import com.algamoney.algamoney.categoria.api.assembler.CategoriaModelAssembler;
import com.algamoney.algamoney.categoria.api.model.CategoriaInput;
import com.algamoney.algamoney.categoria.api.model.CategoriaModel;
import com.algamoney.algamoney.categoria.domain.service.CategoriaService;
import com.algamoney.algamoney.common.api.util.ApiUtil;
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
@RequestMapping(path = "/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaController {

	private final CategoriaService categoriaService;

	private final CategoriaModelAssembler categoriaModelAssembler;
	private final CategoriaInputAssembler categoriaInputAssembler;

	@GetMapping
	@CheckSecurity.Categoria.PodeConsultar
	public ResponseEntity<List<CategoriaModel>> listar() {
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
