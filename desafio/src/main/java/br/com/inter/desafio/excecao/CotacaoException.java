package br.com.inter.desafio.excecao;

/**
 * Classe responsavel por tratar exceções de regra de negocio
 * @author Júlio Silva
 *
 */
public class CotacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public CotacaoException(String message) {
        super(message);
    }
	
}
