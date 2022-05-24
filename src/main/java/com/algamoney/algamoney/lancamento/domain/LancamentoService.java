package com.algamoney.algamoney.lancamento.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.repository.LancamentoRepository;
import com.algamoney.algamoney.lancamento.infrastructure.repository.spec.LancamentoSpec;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LancamentoService {

	private final LancamentoRepository lancamentoRepository;
	
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll(LancamentoSpec.filter());
	}
}
