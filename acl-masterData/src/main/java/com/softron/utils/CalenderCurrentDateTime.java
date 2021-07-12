package com.softron.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class CalenderCurrentDateTime {
	public static String getCurrentDate(String formate) {

		String currentDateTimeInString;
		DateFormat df = new SimpleDateFormat(formate);
		Calendar calobj = Calendar.getInstance();
		currentDateTimeInString=df.format(calobj.getTime());

		return currentDateTimeInString;

	}
}
