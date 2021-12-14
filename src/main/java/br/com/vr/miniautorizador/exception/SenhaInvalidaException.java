package br.com.vr.miniautorizador.exception;

import br.com.vr.miniautorizador.modelo.MensagemErro;

public class SenhaInvalidaException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException() {
        super(MensagemErro.SENHA_INVALIDA.toString());
    }
}
