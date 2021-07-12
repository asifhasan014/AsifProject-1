package com.softron.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

public final class DateTimeUtilConversionService {

	//formate="yyyy-MM-dd'T'HH:mm:ss"
	
	public static Date getCurrentDateTime(String formate) {

		DateFormat df = new SimpleDateFormat(formate);
		Date dateobj = new Date();
		df.format(dateobj);

		return dateobj;

	}

	public static String getStringFromDate(Date date, String formate) {

		DateFormat df = new SimpleDateFormat(formate);

		String text = df.format(date);

		return text;

	}

	public static String getStringFromLocalDateTime(LocalDateTime date, String formate) {

		DateFormat dateFormat = new SimpleDateFormat(formate);

		String dateAsString = dateFormat.format(date);

		return dateAsString;

	}

	public static Date getDateFromString(String text, String formate) {

		Date dayStartTime = null;

		try {
			DateFormat sdf = new SimpleDateFormat(formate);

			dayStartTime = sdf.parse(text);

		} catch (Exception e) {
			System.out.println(e);
		}

		return dayStartTime;

	}
	
	public static Date getDateFromLongValue(Long value, String formate){
		
		Date dateAndTime=null;
		try {
			Date currentDate = new Date(value);

			DateFormat df = new SimpleDateFormat(formate);

			String dateString= df.format(currentDate);
			System.out.println("Milliseconds to Date: " + dateString);
			
			dateAndTime = getDateFromString(dateString,formate);
			
		} catch (Exception e) {
			System.out.println(e);
			
		}
		return dateAndTime;
	}

}
