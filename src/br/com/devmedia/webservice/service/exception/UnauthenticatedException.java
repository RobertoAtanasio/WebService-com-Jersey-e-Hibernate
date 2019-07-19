package br.com.devmedia.webservice.service.exception;

public class UnauthenticatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UnauthenticatedException(String message) {
		super(message);
	}
}