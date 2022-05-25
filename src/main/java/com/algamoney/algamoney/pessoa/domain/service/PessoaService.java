package com.algamoney.algamoney.pessoa.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algamoney.algamoney.pessoa.domain.exception.PessoaInUseException;
import com.algamoney.algamoney.pessoa.domain.exception.PessoaNotFoundException;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa_;
import com.algamoney.algamoney.pessoa.domain.repository.PessoaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PessoaService {

	private final PessoaRepository pessoaRepository;
	
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	public Pessoa buscar(UUID pessoaId) {
		return pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNotFoundException(pessoaId));
	}
	
	@Transactional
	public Pessoa adicionar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional
	public Pessoa atualizar(UUID pessoaId, Pessoa pessoaAtualizada) {
		var pessoa = buscar(pessoaId);
		
		BeanUtils.copyProperties(pessoaAtualizada, pessoa, Pessoa_.ID);
		
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional
	public void remover(UUID pessoaId) {
		try {
			pessoaRepository.deleteById(pessoaId);
			pessoaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PessoaNotFoundException(pessoaId);
		} catch (DataIntegrityViolationException e) {
			throw new PessoaInUseException(pessoaId);
		}
	}
	
	@Transactional
	public void ativar(UUID pessoaId) {
		var pessoa = buscar(pessoaId);
		pessoa.ativar();
	}
	
	@Transactional
	public void inativar(UUID pessoaId) {
		var pessoa = buscar(pessoaId);
		pessoa.inativar();
	}
}
