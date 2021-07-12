package com.softron.mailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softron.mailer.domain.AttachmentEmail;
import com.softron.mailer.domain.HtmlEmail;
import com.softron.mailer.domain.SimpleEmail;
import com.softron.mailer.exeptions.EmailException;
import com.softron.mailer.sender.EmailSender;

@Service
public class EmailService {

	@Autowired
	@Qualifier("simpleEmailSender")
	private EmailSender simpleEmailSender;

	@Autowired
	@Qualifier("htmlEmailSender")
	private EmailSender htmlEmailSender;

	@Autowired
	@Qualifier("attachEmailSender")
	private EmailSender attachmentEmailSender;

	public void sendEmail(final SimpleEmail email) throws EmailException {
		simpleEmailSender.send(email);
	}

	public void sendEmail(final HtmlEmail email) throws EmailException {
		htmlEmailSender.send(email);
	}

	public void sendEmail(final AttachmentEmail email) throws EmailException {
		attachmentEmailSender.send(email);
	}

}
