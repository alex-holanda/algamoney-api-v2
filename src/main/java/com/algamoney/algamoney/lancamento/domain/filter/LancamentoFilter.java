package com.algamoney.algamoney.lancamento.domain.filter;

import java.time.YearMonth;

import com.algamoney.algamoney.lancamento.domain.model.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFilter {

	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	private YearMonth vencimento;
}
