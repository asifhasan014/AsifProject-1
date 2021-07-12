package com.softron.mailer.domain;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Attachment {

	private String name;

	private File file;

}
