package com.algamoney.algamoney.pessoa.domain.repository;

import java.util.UUID;

import com.algamoney.algamoney.common.jpa.CustomJpaRepository;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;

public interface PessoaRepository extends CustomJpaRepository<Pessoa, UUID> {

}
