package com.algamoney.algamoney.lancamento.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.algamoney.algamoney.categoria.domain.model.Categoria;
import com.algamoney.algamoney.pessoa.domain.model.Pessoa;
import com.algamoney.algamoney.security.domain.model.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "lancamento")
@EntityListeners(AuditingEntityListener.class)
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	private String descricao;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate vencimento;
	
	private OffsetDateTime pagamento;
	
	private BigDecimal valor;
	
	private String observacao;
	
	@ManyToOne
	@CreatedBy
	private Usuario createdBy;
	
	@ManyToOne
	@LastModifiedBy
	private Usuario updatedBy;
}
