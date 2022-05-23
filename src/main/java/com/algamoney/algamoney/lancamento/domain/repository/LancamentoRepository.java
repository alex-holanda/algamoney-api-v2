package com.algamoney.algamoney.lancamento.domain.repository;

import java.util.UUID;

import com.algamoney.algamoney.common.jpa.CustomJpaRepository;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;

public interface LancamentoRepository extends CustomJpaRepository<Lancamento, UUID> {

}
