package com.change_vision.astah.quick.command.exception;


@SuppressWarnings("serial")
public class SameNameExistsException extends RuntimeException {

	public SameNameExistsException() {
		super();
	}

	public SameNameExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SameNameExistsException(String message) {
		super(message);
	}

	public SameNameExistsException(Throwable cause) {
		super(cause);
	}

}
