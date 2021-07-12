package com.softron.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.softron.core.annotations.ApiController;
import com.softron.mailer.domain.SimpleEmail;
import com.softron.mailer.exeptions.EmailException;
import com.softron.mailer.service.EmailService;

@ApiController
public class MailTestController {

	@Autowired
	private EmailService emailService;

	@GetMapping("/api/greetings")
	public String sendWelcomeEmail() throws EmailException {
		try {
			emailService.sendEmail(new SimpleEmail(Arrays.asList("mozahidone@gmail.com"), "Welcome to Intellier",
					"Hi Mozahid, welcome to Nidle system."));
		} catch (EmailException ex) {
			throw new EmailException("Error while sending email", ex);
		}
		return "OK";
	}

}
