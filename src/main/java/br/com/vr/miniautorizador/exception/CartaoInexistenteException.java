package br.com.vr.miniautorizador.exception;

import br.com.vr.miniautorizador.modelo.MensagemErro;

public class CartaoInexistenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CartaoInexistenteException() {
        super(MensagemErro.CARTAO_INEXISTENTE.toString());
    }
}
