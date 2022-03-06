package com.marcelo.cursomc.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer status;
	private String errorMessage;
	private Long timeStamp;
	
	public StandardError(Integer status, String errorMessage, Long timeStamp) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
		this.timeStamp = timeStamp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	
}
