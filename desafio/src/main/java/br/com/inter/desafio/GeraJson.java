package br.com.inter.desafio;

import com.google.gson.Gson;

import br.com.inter.desafio.dto.Entrada;
import br.com.inter.desafio.dto.Remessa;

public class GeraJson {

	
	
	public static void main(String[] args) {
		Entrada entrada = new Entrada();
		Remessa remessa = new Remessa();
		
		remessa.setBeneficiario("11111111111");
		remessa.setDepositante("22222222222");
		remessa.setMoeda("DOLAR");
		remessa.setValor("100,00");
		
		entrada.setRemessa(remessa);
		entrada.setDataEvento("06/10/2023 20:20");
		entrada.setResponsavel("Julio");
		
		Gson gson = new Gson();
		
		String json = gson.toJson(entrada);
		System.out.println("JSON: "+json);
	}

}
