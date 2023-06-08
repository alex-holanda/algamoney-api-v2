package com.algamoney.algamoney.pessoa.domain.model;

import java.util.UUID;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "pessoa")
@EntityListeners(AuditingEntityListener.class)
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String nome;
	
	private Boolean ativo;
	
	@Embedded
	private Endereco endereco;
	
	public void ativar() {
		setAtivo(Boolean.TRUE);
	}
	
	public void inativar() {
		setAtivo(Boolean.FALSE);
	}
	
	public boolean isInativo() {
		return !this.ativo;
	}
}
