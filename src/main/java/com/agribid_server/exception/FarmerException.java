package com.agribid_server.exception;

public class FarmerException extends RuntimeException{

	private final String message;
	
	public FarmerException(String msg) {
		super();
		this.message = msg;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
