package br.com.inter.desafio.dto;

import java.math.BigDecimal;

/**
 * DTO responsavel por mapear um operação de remessa
 * @author julio.silva
 */
public class Remessa {
	
	private String depositante;
	private String beneficiario;
	private String moeda;
	private String valor;
	private BigDecimal valorConvertido;
	
	public String getDepositante() {
		return depositante;
	}
	public void setDepositante(String depositante) {
		this.depositante = depositante;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getMoeda() {
		return moeda;
	}
	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public BigDecimal getValorConvertido() {
		return valorConvertido;
	}
	public void setValorConvertido(BigDecimal valorConvertido) {
		this.valorConvertido = valorConvertido;
	}

}
