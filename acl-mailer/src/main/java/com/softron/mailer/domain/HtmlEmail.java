package com.softron.mailer.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HtmlEmail extends Email {

	public HtmlEmail(List<String> recipients, String subject, String body) {
		super(recipients, subject, body, true);
	}

}
