package com.algamoney.algamoney.categoria.domain.repository;

import java.util.UUID;

import com.algamoney.algamoney.categoria.domain.model.Categoria;
import com.algamoney.algamoney.common.jpa.CustomJpaRepository;

public interface CategoriaRepository extends CustomJpaRepository<Categoria, UUID> {

}
