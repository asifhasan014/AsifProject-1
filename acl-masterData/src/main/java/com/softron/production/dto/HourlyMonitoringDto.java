package com.softron.production.dto;

import lombok.Data;

@Data
public class HourlyMonitoringDto {

	private String line;
	private String buyer;
	private String style;
	private String item;
	private double smv;
	private int planHour;
	private int workingHour;
	private int operator;
	private int helper;
	private int hourlyTarget;
	private int totalTarget;
	private int hour;
	private String hourlyProduction;
	private String hourlyDhu;
	private int totalProduction;
	private String totalDhu;
	private String lineAchivment;
	private String produceMinute;
	private String avaiableMinute;
	private String efficiency;
	private String dhu;
}
