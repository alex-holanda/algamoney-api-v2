package com.algamoney.algamoney.lancamento.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.projection.ResumoLancamentoProjection;

public interface LancamentoRepositoryQueries {

	Page<ResumoLancamentoProjection> resumir(LancamentoFilter filter, Pageable pageable);
}
