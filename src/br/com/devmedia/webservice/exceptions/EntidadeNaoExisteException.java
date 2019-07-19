package br.com.devmedia.webservice.exceptions;

public class EntidadeNaoExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoExisteException(String s) {
        super(s);
    }
}
