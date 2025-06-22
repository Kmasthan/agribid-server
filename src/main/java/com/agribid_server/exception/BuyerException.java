package com.agribid_server.exception;

public class BuyerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String message;

	public BuyerException(String msg) {
		super();
		this.message = msg;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
