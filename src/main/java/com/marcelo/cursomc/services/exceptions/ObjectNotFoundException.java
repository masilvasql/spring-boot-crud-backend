package com.marcelo.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
	
	public ObjectNotFoundException(String exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
	}

}
