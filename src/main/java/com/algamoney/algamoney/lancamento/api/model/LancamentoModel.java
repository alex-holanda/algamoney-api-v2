package com.algamoney.algamoney.lancamento.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.algamoney.algamoney.categoria.api.model.CategoriaModel;
import com.algamoney.algamoney.pessoa.api.model.PessoaSummaryModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoModel {

	private UUID id;

	private String tipo;
	
	private String descricao;

	private PessoaSummaryModel pessoa;

	private CategoriaModel categoria;

	private LocalDate vencimento;

	private OffsetDateTime pagamento;

	private BigDecimal valor;

	private String observacao;
}
