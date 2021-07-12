package com.softron.masterdata.capacitystudy.dto;

import lombok.Data;

@Data
public class DailyHeaderInfoDto {

	private String name;
	private String userName;
	private String dateData;
	private Integer dailyTotal;
	private String monthData;
	private Integer monthTotal;
}
