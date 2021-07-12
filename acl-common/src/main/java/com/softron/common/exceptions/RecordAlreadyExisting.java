package com.softron.common.exceptions;

public class RecordAlreadyExisting extends RuntimeException {

	private static final long serialVersionUID = 3280706925355234419L;

	public RecordAlreadyExisting(String message) {
		super(message);
	}

	public RecordAlreadyExisting(Throwable th) {
		super(th);
	}

	public RecordAlreadyExisting(String message, Throwable th) {
		super(message, th);
	}

}
