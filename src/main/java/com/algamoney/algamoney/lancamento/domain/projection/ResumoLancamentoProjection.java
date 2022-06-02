package com.algamoney.algamoney.lancamento.domain.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.algamoney.algamoney.lancamento.domain.model.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoLancamentoProjection {

	private UUID id;
	private TipoLancamento tipo;
	private String descricao;
	private LocalDate vencimento;
	private OffsetDateTime pagamento;
	private BigDecimal valor;
	private String categoria;
	private String pessoa;
}
