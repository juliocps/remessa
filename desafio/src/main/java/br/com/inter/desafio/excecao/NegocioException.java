package br.com.inter.desafio.excecao;

/**
 * Classe responsavel por tratar exceções de regra de negocio
 * @author Júlio Silva
 *
 */
public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
        super(message);
    }
	
}
