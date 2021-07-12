package com.softron.mailer.exeptions;

public class EmailSendingException extends RuntimeException {

	private static final long serialVersionUID = 2741734881324400957L;

	public EmailSendingException(String message) {
		super(message);
	}

}
