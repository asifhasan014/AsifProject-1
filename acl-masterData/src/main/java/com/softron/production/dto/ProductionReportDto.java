package com.softron.production.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductionReportDto {

	private String company;
	private String organization;
	
	private String line; 
	private String productionDate;
	private String productionHour;
	private String production;
	private String orderNo;
	private String styleName;
	private String buyer;
	private String smv;
	private String efficiency;
	
	private List<String> organizationList; 
	
	
	
}
