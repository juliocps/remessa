package br.com.inter.desafio.dto;

public class Value{
	
	private Double cotacaoCompra;

	public Double getCotacaoCompra() {		
		Double scale = Math.pow(10, 2);
		return Math.round(cotacaoCompra * scale) / scale;
	}

	public void setCotacaoCompra(Double cotacaoCompra) {
		this.cotacaoCompra = cotacaoCompra;
	}
}
