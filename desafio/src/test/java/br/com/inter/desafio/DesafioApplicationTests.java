package br.com.inter.desafio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.inter.desafio.controller.RemessaController;
import br.com.inter.desafio.dto.Entrada;
import br.com.inter.desafio.dto.Remessa;
import br.com.inter.desafio.excecao.CotacaoException;
import br.com.inter.desafio.excecao.NegocioException;
import br.com.inter.desafio.service.RemessaService;

/**
 * Classe responsavel por implementar os testes da aplicacao
 * @author julio.silva
 */
@SpringBootTest
class DesafioApplicationTests {
	
	@Autowired
	RemessaController controller;
	
	@Autowired
	RemessaService remessaService;
	
	/**
	 * Teste para validar a entrada do objeto
	 * @author julio.silva
	 */
	@Test
    public void esperaRetornarVazio() {		
     String resultado = controller.validarRemessa(mockEntrada());
     assertEquals("",resultado);    
	}
	
	/**
	 * Teste para validar o tratamento dos dados recebidos
	 * @author julio.silva
	 */
	@Test
    public void esperaRetornarErroValor() {		
	 Entrada entrada = mockEntrada();
	 entrada.getRemessa().setValor("100,000");
     String resultado = controller.validarRemessa(entrada);
     assertEquals("O Valor da Remessa deve ser informado com duas casas após a virgula.",resultado);    
	}
	
	/**
	 * Teste para validar o fluxo completo da remessa
	 * @author julio.silva
	 */
	@Test
    public void esperaRemessaEfetuadaComSucesso() {		
	    String resultado = "";
		try {
			resultado = remessaService.efetuarRemessa(mockEntrada().getRemessa());
		
		} catch (NegocioException e) {		
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (CotacaoException e) {
			e.printStackTrace();
		}
	     assertEquals("Operação Efetuada com sucesso",resultado);    
	}

	public Entrada mockEntrada() {
		
		Entrada entrada = new Entrada();
		Remessa remessa = new Remessa();
		
		remessa.setBeneficiario("11111111111");
		remessa.setDepositante("11111111111");
		remessa.setValor("50,00");
		
		entrada.setRemessa(remessa);
		entrada.setDataEvento("08/10/2023 20:20");
		entrada.setResponsavel("Julio");
		
		return entrada;
		
	}
}
