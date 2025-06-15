package com.agribid_server.exception;

public class RegionDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String message;

	public RegionDataException(String msg) {
		super();
		this.message = msg;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
