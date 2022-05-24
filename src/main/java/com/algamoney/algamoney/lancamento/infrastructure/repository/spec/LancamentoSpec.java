package com.algamoney.algamoney.lancamento.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento_;

public class LancamentoSpec {

	public static Specification<Lancamento> filter() {
		return (root, query, builder) -> {
			if (Lancamento.class.equals(query.getResultType())) {
				root.fetch(Lancamento_.CATEGORIA);
				root.fetch(Lancamento_.PESSOA);
			}
			
			return builder.and();
		};
	}
}
