package com.algamoney.algamoney.lancamento.domain.service;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algamoney.algamoney.categoria.domain.service.CategoriaService;
import com.algamoney.algamoney.common.domain.BusinessException;
import com.algamoney.algamoney.common.domain.EntityNotFoundException;
import com.algamoney.algamoney.lancamento.domain.exception.LancamentoNotFoundException;
import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento_;
import com.algamoney.algamoney.lancamento.domain.repository.LancamentoRepository;
import com.algamoney.algamoney.lancamento.infrastructure.repository.spec.LancamentoSpec;
import com.algamoney.algamoney.pessoa.domain.exception.PessoaInativaException;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;
import com.algamoney.algamoney.pessoa.domain.service.PessoaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LancamentoService {

	private final LancamentoRepository lancamentoRepository;

	private final CategoriaService categoriaService;
	private final PessoaService pessoaService;

	public Page<Lancamento> listar(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.findAll(LancamentoSpec.filter(filter), pageable);
	}

	public Lancamento buscar(UUID lancamentoId) {
		return lancamentoRepository.findById(lancamentoId)
				.orElseThrow(() -> new LancamentoNotFoundException(lancamentoId));
	}

	@Transactional
	public Lancamento adicionar(Lancamento lancamento) {
		try {
			var pessoa = pessoaService.buscar(lancamento.getPessoa().getId());
			var categoria = categoriaService.buscar(lancamento.getCategoria().getId());

			validatePessoaInativa(pessoa);

			lancamento.setCategoria(categoria);
			lancamento.setPessoa(pessoa);

			return lancamentoRepository.save(lancamento);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Transactional
	public Lancamento atualizar(UUID lancamentoId, Lancamento lancamentoAtualizado) {
		try {
			var categoria = categoriaService.buscar(lancamentoAtualizado.getCategoria().getId());
			var pessoa = pessoaService.buscar(lancamentoAtualizado.getPessoa().getId());
			
			validatePessoaInativa(pessoa);
			
			var lancamento = buscar(lancamentoId);
			
			BeanUtils.copyProperties(lancamentoAtualizado, lancamento, Lancamento_.ID, Lancamento_.CATEGORIA, Lancamento_.PESSOA);
			
			lancamento.setCategoria(categoria);
			lancamento.setPessoa(pessoa);

			return lancamentoRepository.save(lancamento);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	private void validatePessoaInativa(Pessoa pessoa) {
		if (pessoa.isInativo()) {
			throw new PessoaInativaException(pessoa.getId());
		}
	}
}
