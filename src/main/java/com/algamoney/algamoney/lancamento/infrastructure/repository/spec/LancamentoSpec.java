package com.algamoney.algamoney.lancamento.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.infrastructure.repository.util.LancamentoUtil;

public class LancamentoSpec {

	public static Specification<Lancamento> filter(LancamentoFilter filter) {
		return (root, query, builder) -> {
			if (Lancamento.class.equals(query.getResultType())) {
				root.fetch("categoria");
				root.fetch("pessoa");
				root.fetch("updatedBy");
				root.fetch("createdBy");
			}

			var predicates = LancamentoUtil.criarRestricoes(filter, root, builder);

			return builder.and(predicates);
		};
	}

	
}
