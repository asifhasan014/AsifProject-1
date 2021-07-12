package com.softron.mailer.exeptions;

public class EmailException extends Exception {

	private static final long serialVersionUID = 7578508652155262544L;

	public EmailException(String message) {
		super(message);
	}

	public EmailException(Throwable th) {
		super(th);
	}

	public EmailException(String message, Throwable th) {
		super(message, th);
	}
}
