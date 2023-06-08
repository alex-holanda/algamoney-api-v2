package com.algamoney.algamoney.lancamento.infrastructure.repository;


import com.algamoney.algamoney.lancamento.domain.filter.LancamentoFilter;
import com.algamoney.algamoney.lancamento.domain.model.Lancamento;
import com.algamoney.algamoney.lancamento.domain.projection.ResumoLancamentoProjection;
import com.algamoney.algamoney.lancamento.domain.repository.LancamentoRepositoryQueries;
import com.algamoney.algamoney.lancamento.infrastructure.repository.util.LancamentoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class LancamentoRepositoryImpl implements LancamentoRepositoryQueries {

	private final EntityManager entityManager;

	@Override
	public Page<ResumoLancamentoProjection> resumir(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamentoProjection> criteria = builder.createQuery(ResumoLancamentoProjection.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(ResumoLancamentoProjection.class, root.get("id"),
				root.get("tipo"), root.get("descricao"), root.get("vencimento"),
				root.get("pagamento"), root.get("valor"),
				root.get("categoria").get("nome"), root.get("pessoa").get("nome")));

		Predicate[] predicates = LancamentoUtil.criarRestricoes(filter, root, builder);

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

		Predicate[] predicates = LancamentoUtil.criarRestricoes(filter, root, builder);
		criteria.where(predicates);

		return entityManager.createQuery(criteria).getSingleResult();
	}
}
