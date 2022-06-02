package com.algamoney.algamoney.lancamento.infrastructure.repository;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.algamoney.algamoney.categoria.domain.model.Categoria_;
import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento_;
import com.algamoney.algamoney.lancamento.domain.projection.ResumoLancamentoProjection;
import com.algamoney.algamoney.lancamento.domain.repository.LancamentoRepositoryQueries;
import com.algamoney.algamoney.lancamento.infrastructure.repository.util.LancamentoUtil;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa_;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class LancamentoRepositoryImpl implements LancamentoRepositoryQueries {

	private final EntityManager entityManager;

	@Override
	public Page<ResumoLancamentoProjection> resumir(LancamentoFilter filter, Pageable pageable) {
		var builder = entityManager.getCriteriaBuilder();
		var criteria = builder.createQuery(ResumoLancamentoProjection.class);
		var root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(ResumoLancamentoProjection.class, root.get(Lancamento_.ID),
				root.get(Lancamento_.TIPO), root.get(Lancamento_.DESCRICAO), root.get(Lancamento_.VENCIMENTO),
				root.get(Lancamento_.PAGAMENTO), root.get(Lancamento_.VALOR),
				root.get(Lancamento_.CATEGORIA).get(Categoria_.NOME), root.get(Lancamento_.PESSOA).get(Pessoa_.NOME)));

		var predicates = LancamentoUtil.criarRestricoes(filter, root, builder);

		criteria.where(predicates);

		var query = entityManager.createQuery(criteria);

		LancamentoUtil.adicionarRestricaoDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(LancamentoFilter filter) {
		var builder = entityManager.getCriteriaBuilder();
		var criteria = builder.createQuery(Long.class);
		var root = criteria.from(Lancamento.class);

		criteria.select(builder.count(root));

		var predicates = LancamentoUtil.criarRestricoes(filter, root, builder);
		criteria.where(predicates);

		return entityManager.createQuery(criteria).getSingleResult();
	}
}
