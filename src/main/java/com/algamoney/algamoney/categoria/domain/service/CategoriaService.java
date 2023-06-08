package com.algamoney.algamoney.categoria.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algamoney.algamoney.categoria.domain.exception.CategoriaInUseException;
import com.algamoney.algamoney.categoria.domain.exception.CategoriaNotFoundException;
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

	public Categoria buscar(UUID categoriaId) {
		return categoriaRepository.findById(categoriaId).orElseThrow(() -> new CategoriaNotFoundException(categoriaId));
	}

	@Transactional
	public Categoria adicionar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Transactional
	public Categoria atualizar(UUID categoriaId, Categoria categoriaAtualizada) {
		var categoria = buscar(categoriaId);

		BeanUtils.copyProperties(categoriaAtualizada, categoria, "id");

		return categoria;
	}

	@Transactional
	public void remover(UUID categoriaId) {
		try {
			categoriaRepository.deleteById(categoriaId);
			categoriaRepository.flush();			
		} catch (EmptyResultDataAccessException e) {
			throw new CategoriaNotFoundException(categoriaId);
		} catch (DataIntegrityViolationException e) {
			throw new CategoriaInUseException(categoriaId);
		}

	}
}
