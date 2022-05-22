package com.algamoney.algamoney.categoria.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algamoney.algamoney.categoria.domain.model.Categoria;
import com.algamoney.algamoney.categoria.domain.repository.CategoriaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;

	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	public Categoria getById(UUID categoriaId) {
		return categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new RuntimeException("Categoria não cadastrada"));
	}
	
	@Transactional
	public Categoria adicionar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
}
