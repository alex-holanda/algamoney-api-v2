package com.algamoney.algamoney.lancamento.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algamoney.algamoney.categoria.domain.model.Categoria_;
import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento_;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa_;

public class LancamentoSpec {

	public static Specification<Lancamento> filter(LancamentoFilter filter) {
		return (root, query, builder) -> {
			if (Lancamento.class.equals(query.getResultType())) {
				root.fetch(Lancamento_.CATEGORIA);
				root.fetch(Lancamento_.PESSOA);
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getTipo() != null) {
				predicates.add(builder.equal(root.get(Lancamento_.TIPO), filter.getTipo()));
			}
			
			if (filter.getPessoa() != null) {
				predicates.add(builder.like(root.get(Lancamento_.PESSOA).get(Pessoa_.NOME), "%" + filter.getPessoa() + "%"));
			}
			
			if (filter.getCategoria() != null) {
				predicates.add(builder.like(root.get(Lancamento_.CATEGORIA).get(Categoria_.NOME), "%" + filter.getCategoria() + "%"));
			}
			
			if (filter.getVencimento() != null) {
				var filterYearMonth = filter.getVencimento();
				var startsOn = filterYearMonth.atDay(1);
				var endsOn = filterYearMonth.atDay(filterYearMonth.lengthOfMonth());
				
				predicates.add(builder.between(root.get(Lancamento_.VENCIMENTO), startsOn, endsOn));
			}
			
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
