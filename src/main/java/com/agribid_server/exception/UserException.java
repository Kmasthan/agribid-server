package com.agribid_server.exception;

public class UserException extends RuntimeException {
	private final String message;

	public UserException(String msg) {
		super();
		this.message = msg;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
