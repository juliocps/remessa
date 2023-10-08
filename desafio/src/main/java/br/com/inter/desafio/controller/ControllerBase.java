package br.com.inter.desafio.controller;





import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.Gson;

import br.com.inter.desafio.dto.Retorno;
import lombok.extern.log4j.Log4j;

/**
 * Classe comum para todos os controllers ( reuso )
 * @author julio.silva
 */
@Log4j
public class ControllerBase {
	
	private static final String HTTP_CODE_ERRO = "500";
	private static final String HTTP_CODE_SUCESSO = "200";

	public final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public Gson gson;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dataHora = new Date();

	public Date getDataHora() {
		return dataHora;
	}
	
	/**
	 * Metodo responsavel por montar a mensagem quando ocorrer um erro interno
	 * @param String path, String msgErro
	 * @return Retorno dto
	 */
	public Retorno montarMensagemErro(String path, String msgErro) {
		Retorno retorno = new Retorno();
		retorno.setPath(path);
		retorno.setStatus(HTTP_CODE_ERRO);
		retorno.setMensagem(msgErro);
		return retorno;
	} 
	
	/**
	 * Metodo responsavel por montar a mensagem de sucesso
	 * @param String path, String msg
	 * @return Retorno dto
	 */
	public Retorno montarMensagemSucesso(String path, String msg) {
		Retorno retorno = new Retorno();
		retorno.setPath(path);
		retorno.setStatus(HTTP_CODE_SUCESSO);
		retorno.setMensagem(msg);
		return retorno;
	} 
	
	
}
