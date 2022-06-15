package com.algamoney.algamoney.pessoa.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.algamoney.algamoney.pessoa.domain.filter.PessoaFilter;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa_;

public class PessoaSpec {

	public static Specification<Pessoa> filter(PessoaFilter pessoaFilter) {
		return (root, query, builder) -> {

			var predicates = new ArrayList<Predicate>();

			if (StringUtils.hasText(pessoaFilter.getNome())) {
				predicates.add(builder.like(root.get(Pessoa_.NOME), "%" + pessoaFilter.getNome() + "%"));
			}

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
