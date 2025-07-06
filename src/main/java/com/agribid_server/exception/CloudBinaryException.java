package com.agribid_server.exception;

public class CloudBinaryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final String message;

	public CloudBinaryException(String msg) {
		this.message = msg;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
