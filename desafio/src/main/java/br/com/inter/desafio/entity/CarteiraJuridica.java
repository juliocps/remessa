package br.com.inter.desafio.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa a Carteira da Pessoa Juridica 
 * As carteiras estao entidades distintas para garantir o 
 * relacionamento via integridade do banco de dados e separando
 * os assuntos para um possivel alto volume de dados.
 * @author julio.silva
 */
@Entity
@Table(name="carteira_juridica")
public class CarteiraJuridica extends Carteira{
	

	private PessoaJuridica pessoaJuridica;

	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	public Integer getId() {
		return id;
	}

	@Override
	@Column(length = 5, nullable = false)
	public String getMoeda() {
		return moeda;
	}

	@Override
	@Column(length = 50, nullable = false)
	public BigDecimal getValor() {
		return valor;
	}

	@ManyToOne
    @JoinColumn(name = "pessoaJuridica" )
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
}
