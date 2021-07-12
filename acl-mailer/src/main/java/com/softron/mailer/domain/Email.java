package com.softron.mailer.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Email {

	private final List<String> recipients;

	private final String subject;

	private final String body;

	private final boolean html;

	private final Attachment attachment;

	public Email(List<String> recipients, String subject, String body) {
		super();
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
		this.html = false;
		this.attachment = null;
	}

	public Email(List<String> recipients, String subject, String body, boolean html) {
		super();
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
		this.html = html;
		this.attachment = null;
	}

	public Email(List<String> recipients, String subject, String body, Attachment attachment) {
		super();
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
		this.html = true;
		this.attachment = attachment;
	}
	
	public String[] getAllRecipients() {
		final String[] receipients = new String[recipients.size()];
		return recipients.toArray(receipients);
	}

}
