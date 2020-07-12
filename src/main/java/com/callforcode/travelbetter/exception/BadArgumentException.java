package com.callforcode.travelbetter.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadArgumentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadArgumentException() {
		super();
	}

	public BadArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadArgumentException(String message) {
		super(message);
	}

	public BadArgumentException(Throwable cause) {
		super(cause);
	}
}