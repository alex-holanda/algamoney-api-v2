package com.algamoney.algamoney.lancamento.infrastructure.repository.util;

import java.util.ArrayList;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algamoney.algamoney.categoria.domain.model.Categoria_;
import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento_;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa_;

public class LancamentoUtil {

	public static Predicate[] criarRestricoes(LancamentoFilter filter, Root<Lancamento> root, CriteriaBuilder builder) {
		var predicates = new ArrayList<Predicate>();

		if (filter.getTipo() != null) {
			predicates.add(builder.equal(root.get(Lancamento_.TIPO), filter.getTipo()));
		}

		if (StringUtils.hasText(filter.getPessoa())) {
			predicates
					.add(builder.like(root.get(Lancamento_.PESSOA).get(Pessoa_.NOME), "%" + filter.getPessoa() + "%"));
		}

		if (StringUtils.hasText(filter.getCategoria())) {
			predicates.add(builder.like(root.get(Lancamento_.CATEGORIA).get(Categoria_.NOME),
					"%" + filter.getCategoria() + "%"));
		}

		if (filter.getVencimento() != null) {
			var filterYearMonth = filter.getVencimento();
			var startsOn = filterYearMonth.atDay(1);
			var endsOn = filterYearMonth.atDay(filterYearMonth.lengthOfMonth());

			predicates.add(builder.between(root.get(Lancamento_.VENCIMENTO), startsOn, endsOn));
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
