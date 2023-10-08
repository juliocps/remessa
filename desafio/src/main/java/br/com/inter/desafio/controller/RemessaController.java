package br.com.inter.desafio.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.inter.desafio.dto.Entrada;
import br.com.inter.desafio.dto.Retorno;
import br.com.inter.desafio.excecao.CotacaoException;
import br.com.inter.desafio.excecao.NegocioException;
import br.com.inter.desafio.service.RemessaService;

/**
 * Classe com os endpoints responsaveis por efeutar a operação de remessa
 * @author julio.silva
 */
@Controller
@RequestMapping("/remessa")
public class RemessaController extends ControllerBase {
	
	private static final int TAMANHO_CNPJ = 14;
	private static final int TAMANHO_CPF = 11;
	private static final int TAMANHO_CENTAVOS = 2;
	
	@Autowired
	RemessaService remessaService;
	
	
	/**
	 * Metodo responsavel por expor o endpoint da remessa
	 * @param Entrada dto
	 * @return Retorno dto
	 */
	@ResponseBody
    @PostMapping(produces = "application/json;charset=UTF-8")
    public Retorno efetuarRemessa(@RequestBody Entrada dto) {
		Retorno retorno = new Retorno();
		String PATH_WS = "/remessa/post";
		Date dataAtual = new Date();
         try {
        	 log.info("Iniciando a Remessa: " + buscarIdRequisicao(dataAtual));
        	 
        	 String msgErro =  validarRemessa(dto);
        	 
        	 
        	 if(msgErro.isEmpty()) { 
        		 log.info("Auditoria da Remessa: " + buscarIdRequisicao(dataAtual) + " | Responsavel: " + dto.getResponsavel() + " | Data de envio:" + dto.getDataEvento());
        		 
        		 log.info("Remessa (" + buscarIdRequisicao(dataAtual) + ") DE: " + dto.getRemessa().getDepositante() + " PARA: "
        				 + dto.getRemessa().getBeneficiario() + " NO VALOR DE: " + dto.getRemessa().getValor());
        		 
        		 String resultado = remessaService.efetuarRemessa(dto.getRemessa());
        		 retorno = montarMensagemSucesso(PATH_WS, resultado);
        	 }else {  
        		log.info("Falha na validação da Remessa (" + buscarIdRequisicao(dataAtual) + ") | Mensagem: " + msgErro);        		 
				retorno = montarMensagemErro(PATH_WS, msgErro);	 
        	 } 
        	 
        	 log.info("Finalizando a Remessa: " + buscarIdRequisicao(dataAtual));
        	 return retorno;
         } catch(NegocioException e) {  
        	 log.error("Erro na Remessa (" + buscarIdRequisicao(dataAtual) + ") | ERRO (NegocioException): " + e.getMessage());     
             return montarMensagemErro(PATH_WS, e.getMessage());
         }catch(CotacaoException e) {  
        	 log.error("Erro na Remessa (" + buscarIdRequisicao(dataAtual) + ") | ERRO (CotacaoException): " + e.getMessage());
             return montarMensagemErro(PATH_WS, e.getMessage());
         }catch(Exception e) {
             e.printStackTrace();            
             return montarMensagemErro(PATH_WS, "Ocorreu um erro inesperado! Entre em contato com o administrador do sistema.");
         }
    }


	/**
	 * Metodo responsavel por realizar a validacao de campos
	 * Visando ter melhor experiencia para o o usuário as validações
	 * foram feitas visando retornar uma mensagem amigavel e facil de 
	 * de ser entedida.
	 * O codigo tem um custo mais alto, porém visando atender o requisito não funcional
	 * @param Entrada dto
	 * @return String msgErro
	 */
	public String validarRemessa(Entrada dto) {
		StringBuilder msgErro = new StringBuilder();
		
		if(dto != null) {
   		 if(dto.getDataEvento().isEmpty()) {
   			 msgErro.append("Data do evento não pode ser nula ou vazia.");
   		 }
   		 if(dto.getResponsavel().isEmpty()) {
   			 msgErro.append("O Responsável não pode ser nulo ou vazio.");
   		 }
   		 if(dto.getRemessa()==null) {
   			 msgErro.append("A Remessa não pode ser nula.");
   		 }else {  			 		 	
   			 if(dto.getRemessa().getBeneficiario().isEmpty()) {
   				 msgErro.append("O Beneficiario da Remessa não pode ser nulo ou vazio.");
   			 }else {
   				if(dto.getRemessa().getBeneficiario().length() == TAMANHO_CPF || dto.getRemessa().getBeneficiario().length() == TAMANHO_CNPJ) { //não faz nada  					
   				}else {
   					msgErro.append("CPF ou CNPJ do beneficiário está incorreto.");
   				}
   				if(!dto.getRemessa().getBeneficiario().matches("[0-9]*")) {
   					msgErro.append("CPF ou CNPJ do beneficiário deve conter apenas número.");
   				}
   			 }
   			 if(dto.getRemessa().getDepositante().isEmpty()) {
   				 msgErro.append("O Depositante da Remessa não pode ser nulo ou vazio.");
   			 }else {
   				if(dto.getRemessa().getDepositante().length() == TAMANHO_CPF || dto.getRemessa().getDepositante().length() == TAMANHO_CNPJ) { //não faz nada  					
   				}else {
   					msgErro.append("CPF ou CNPJ do depositante está incorreto.");
   				}
   				if(!dto.getRemessa().getDepositante().matches("[0-9]*")) {
   					msgErro.append("CPF ou CNPJ do depositante deve conter apenas número.");
   				}
   			 }  			 
   			 if(dto.getRemessa().getValor().isEmpty()) {
   				 msgErro.append("O Valor da Remessa não pode ser nulo ou vazio.");
   			 }else {
   				 if(!dto.getRemessa().getValor().contains(",")) {
   					msgErro.append("O Valor da Remessa deve ser informado com os centavos.");
   				}else {
   					String[] vetorValor = dto.getRemessa().getValor().split(",");
   					if(vetorValor[1].length() != TAMANHO_CENTAVOS) {
   						msgErro.append("O Valor da Remessa deve ser informado com duas casas após a virgula.");
   					}
   					
   					if(!vetorValor[0].matches("[0-9]*") || !vetorValor[1].matches("[0-9]*")) {
   	   					msgErro.append("O Valor da Remessa deve ser informado está incorreto.");
   	   				}
   					
   				}
   				
   			 }
   			 
   		 }
	}
		return msgErro.toString();
	}
}