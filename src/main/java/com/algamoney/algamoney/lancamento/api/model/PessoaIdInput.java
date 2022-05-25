package com.algamoney.algamoney.lancamento.api.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaIdInput {

	@NotNull
	private UUID id;
}
