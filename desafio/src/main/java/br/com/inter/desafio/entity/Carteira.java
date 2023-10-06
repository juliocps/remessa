package br.com.inter.desafio.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="carteira")
public class Carteira extends EntityBase{
	
	private String moeda;
	private Integer idPessoaFisica;
	private Integer idPessoaJuridica;
	private BigDecimal valor;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	public Integer getId() {
		return id;
	}

	@Column(length = 5, nullable = false)
	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	@Column(name = "id_pessoa_fisica",length = 10, nullable = true)
	public Integer getIdPessoaFisica() {
		return idPessoaFisica;
	}

	public void setIdPessoaFisica(Integer idPessoaFisica) {
		this.idPessoaFisica = idPessoaFisica;
	}

	@Column(name = "id_pessoa_juridica", length = 50, nullable = true)
	public Integer getIdPessoaJuridica() {
		return idPessoaJuridica;
	}

	
	public void setIdPessoaJuridica(Integer idPessoaJuridica) {
		this.idPessoaJuridica = idPessoaJuridica;
	}

	@Column(length = 50, nullable = false)
	public BigDecimal getValor() {
		return valor;
	}

	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
