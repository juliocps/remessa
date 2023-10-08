package br.com.inter.desafio.dto;

/**
 * DTO responsavel pela retorno generico de todas as integrações
 * @author julio.silva
 */
public class Retorno {
	
	private String status;
	
	private String mensagem;
	
	private String path;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
