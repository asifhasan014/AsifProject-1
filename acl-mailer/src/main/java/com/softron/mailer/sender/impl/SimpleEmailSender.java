package com.softron.mailer.sender.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.softron.mailer.domain.Email;
import com.softron.mailer.exeptions.EmailException;
import com.softron.mailer.sender.EmailSender;

@Service("simpleEmailSender")
public class SimpleEmailSender implements EmailSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailSender.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void send(Email email) throws EmailException {
		LOGGER.info("Sending simple email.");
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email.getAllRecipients());

		msg.setSubject(email.getSubject());
		msg.setText(email.getBody());

		try {
			javaMailSender.send(msg);
		} catch (MailException ex) {
			throw new EmailException(ex);
		}
		LOGGER.info("Email sent successfully.");
	}

}
