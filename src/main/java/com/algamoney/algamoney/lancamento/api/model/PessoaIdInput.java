package com.algamoney.algamoney.lancamento.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PessoaIdInput {

	@NotNull
	private UUID id;
}
