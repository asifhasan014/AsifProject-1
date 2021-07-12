package com.softron.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Service;

public final class DateTimeConversionService {

	public static String getStringFromDate(Date date, String formate) {

		DateFormat df = new SimpleDateFormat(formate);

		String text = df.format(date);

		return text;

	}

	public static Date getDateFromString(String text) {

		// DateFormat df = new SimpleDateFormat(formate);

		Date date = Date.valueOf(text);

		return date;

	}

}
