package com.softron.mailer.sender;

import com.softron.mailer.domain.Email;
import com.softron.mailer.exeptions.EmailException;

public interface EmailSender {

	public void send(Email email) throws EmailException;

}
