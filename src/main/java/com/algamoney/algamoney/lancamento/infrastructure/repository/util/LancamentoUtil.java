package com.algamoney.algamoney.lancamento.infrastructure.repository.util;


import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


import java.util.ArrayList;

public class LancamentoUtil {

	public static Predicate[] criarRestricoes(LancamentoFilter filter, Root<Lancamento> root, CriteriaBuilder builder) {
		var predicates = new ArrayList<Predicate>();

		if (filter.getTipo() != null) {
			predicates.add(builder.equal(root.get("tipo"), filter.getTipo()));
		}

		if (StringUtils.hasText(filter.getPessoa())) {
			predicates
					.add(builder.like(root.get("pessoa").get("nome"), "%" + filter.getPessoa() + "%"));
		}

		if (StringUtils.hasText(filter.getCategoria())) {
			predicates.add(builder.like(root.get("categoria").get("nome"),
					"%" + filter.getCategoria() + "%"));
		}

		if (filter.getVencimento() != null) {
			var filterYearMonth = filter.getVencimento();
			var startsOn = filterYearMonth.atDay(1);
			var endsOn = filterYearMonth.atDay(filterYearMonth.lengthOfMonth());

			predicates.add(builder.between(root.get("vencimento"), startsOn, endsOn));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public static void adicionarRestricaoDePaginacao(TypedQuery<?> query, Pageable pageable) {
		var atual = pageable.getPageNumber();
		var registros = pageable.getPageSize();
		var primeira = atual * registros;

		query.setFirstResult(primeira);
		query.setMaxResults(registros);
	}
}
