package com.agribid_server.exception;

public class UserCommunicationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private final String message;

	public UserCommunicationException(String msg) {
		super();
		this.message = msg;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
