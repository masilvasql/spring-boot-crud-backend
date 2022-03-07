package com.marcelo.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public DataIntegrityException(String exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
	}

}
