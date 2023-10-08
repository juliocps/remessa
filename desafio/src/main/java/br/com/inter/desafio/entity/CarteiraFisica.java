package br.com.inter.desafio.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa a Carteira da Pessoa Fisica 
 * As carteiras estão entidades distintas para garantir o 
 * relacionamento via integridade do banco de dados e separando
 * os assuntos para um possivel alto volume de dados.
 * @author julio.silva
 */
@Entity
@Table(name="carteira")
public class CarteiraFisica extends Carteira{
	
	
	private PessoaFisica pessoaFisica;
	
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

	@Override
	@Column(length = 50)
	public BigDecimal getAcumuladoDia() {
		return acumuladoDia;
	}
	
	@Override
	@Column()
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getUltimaTransacao() {
		return ultimaTransacao;
	}
	
	@ManyToOne
    @JoinColumn(name = "pessoaFisica" )
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

}