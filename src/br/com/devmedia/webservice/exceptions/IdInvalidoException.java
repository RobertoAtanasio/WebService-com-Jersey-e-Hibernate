package br.com.devmedia.webservice.exceptions;

public class IdInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdInvalidoException(String s) {
        super(s);
    }

}