package br.com.inter.desafio.dto;

/**
 * DTO complementar para conversão do json retornado pela api da cotacao
 * @author Júlio Silva
 *
 */
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
