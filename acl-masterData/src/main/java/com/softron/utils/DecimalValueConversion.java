package com.softron.utils;

import java.text.DecimalFormat;

public final class DecimalValueConversion {

	public static double getTwoValueAfterDot(String Value) {

		double value = Double.parseDouble(Value);
		value = Double.parseDouble(new DecimalFormat("0.00").format(value));
		return value;
	}

	public static double getFormattedDoubleValue(double Value) {

		DecimalFormat df = new DecimalFormat("####0.00");
		String st = df.format(Value);
		double num = Double.parseDouble(st);
		
		return num;
	}
	
}
