package com.softron.mailer.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleEmail extends Email {

	public SimpleEmail(List<String> recipients, String subject, String body) {
		super(recipients, subject, body);
	}

}
