package com.algamoney.algamoney.pessoa.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algamoney.algamoney.pessoa.domain.model.Pessoa;
import com.algamoney.algamoney.pessoa.domain.repository.PessoaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PessoaService {

	private final PessoaRepository pessoaRepository;
	
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
}
