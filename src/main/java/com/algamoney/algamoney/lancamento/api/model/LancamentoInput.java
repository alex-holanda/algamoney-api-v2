package com.algamoney.algamoney.lancamento.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.algamoney.algamoney.lancamento.domain.model.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoInput {

	@NotNull
	private TipoLancamento tipo;

	@Valid
	@NotNull
	private PessoaIdInput pessoa;
	
	@NotBlank
	private String descricao;

	@Valid
	@NotNull
	private CategoriaIdInput categoria;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate vencimento;

	private OffsetDateTime pagamento;

	@NotNull
	@Positive
	private BigDecimal valor;

	private String observacao;
}
