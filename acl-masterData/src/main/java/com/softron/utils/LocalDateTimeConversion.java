package com.softron.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class LocalDateTimeConversion {
	
	    //formate="yyyy-MM-dd'T'HH:mm:ss";

	public static LocalDateTime getOnlyDateFromString(String text) {
		// default, ISO_LOCAL_DATE
		// String formate should be "2016-08-16";

		LocalDateTime Date = LocalDateTime.parse(text);

		return Date;

	}

	public static String getStringFromLocalDateTime(LocalDateTime date, String formate) {

		String dateAsString = date.format(DateTimeFormatter.ofPattern(formate));

		return dateAsString;

	}

	public static LocalDateTime getLocalDateTimeFromString(String text, String formate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formate);
		LocalDateTime dateTime = LocalDateTime.parse(text, formatter);

		return dateTime;
	}
}
