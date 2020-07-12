package com.callforcode.travelbetter.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TravelBetterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TravelBetterException() {
		super();
	}

	public TravelBetterException(String message, Throwable cause) {
		super(message, cause);
	}

	public TravelBetterException(String message) {
		super(message);
	}

	public TravelBetterException(Throwable cause) {
		super(cause);
	}
}