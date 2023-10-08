package br.com.inter.desafio.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe Carteira com os campos comuns entre carteira fisica e carteira juridica. 
 * @author julio.silva
 */
public class Carteira  extends EntityBase{ 
	
	protected String moeda;
	protected BigDecimal valor;
	protected BigDecimal acumuladoDia;
	protected Date ultimaTransacao;
		
	public Date getUltimaTransacao() {
		return ultimaTransacao;
	}
	public void setUltimaTransacao(Date ultimaTransacao) {
		this.ultimaTransacao = ultimaTransacao;
	}
	public BigDecimal getAcumuladoDia() {
		return acumuladoDia;
	}
	public void setAcumuladoDia(BigDecimal acumuladoDia) {
		this.acumuladoDia = acumuladoDia;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getMoeda() {
		return moeda;
	}
	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}
	
	public Integer getId() {
		return super.getId();
	}
}
