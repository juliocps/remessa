package br.com.inter.desafio.dto;

/**
 * DTO responsavel pela entrada generica das integrações existentes
 * @author julio.silva
 */
public class Entrada {
	
	private Remessa remessa;
	
	private String dataEvento;
	
	private String responsavel;


	
	public String getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Remessa getRemessa() {
		return remessa;
	}

	public void setRemessa(Remessa remessa) {
		this.remessa = remessa;
	}
}
