package br.com.inter.desafio.excecao;

/**
 * Classe responsavel por tratar excecoes da integracao com a api de cotacao
 * @author Júlio Silva
 *
 */
public class CotacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public CotacaoException(String message) {
        super(message);
    }
	
}
