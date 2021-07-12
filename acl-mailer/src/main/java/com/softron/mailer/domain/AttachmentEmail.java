package com.softron.mailer.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttachmentEmail extends Email {

	public AttachmentEmail(List<String> recipients, String subject, String body, Attachment attachment) {
		super(recipients, subject, body, attachment);
	}

	public AttachmentEmail(List<String> recipients, String subject, String body, boolean html, Attachment attachment) {
		super(recipients, subject, body, html, attachment);
	}

}
