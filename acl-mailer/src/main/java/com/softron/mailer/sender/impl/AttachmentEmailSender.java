package com.softron.mailer.sender.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.softron.mailer.domain.Attachment;
import com.softron.mailer.domain.Email;
import com.softron.mailer.exeptions.EmailException;
import com.softron.mailer.sender.EmailSender;

@Service("attachEmailSender")
public class AttachmentEmailSender implements EmailSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentEmailSender.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void send(Email email) throws EmailException {

		LOGGER.info("Sending email with attachment.");

		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true);
			helper.setTo(email.getAllRecipients());
			helper.setSubject(email.getSubject());
			helper.setText(email.getBody(), email.isHtml());

			Attachment attachment = email.getAttachment();
			helper.addAttachment(attachment.getName(), attachment.getFile());

		} catch (MessagingException ex) {
			throw new EmailException(ex);
		}

		javaMailSender.send(msg);
		LOGGER.info("Email sent successfully.");

	}

}
