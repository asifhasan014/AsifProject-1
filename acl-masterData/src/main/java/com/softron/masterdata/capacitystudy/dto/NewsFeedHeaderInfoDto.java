package com.softron.masterdata.capacitystudy.dto;

import java.util.List;

import lombok.Data;

@Data
public class NewsFeedHeaderInfoDto {


	private String monthData;
	private Integer monthTotal;
	
	private List<DailyHeaderInfoDto> dailyHeaderInfoDtoList;
	 
}
