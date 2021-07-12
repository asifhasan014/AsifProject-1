package com.softron.production.dto;

import lombok.Data;

@Data
public class OrderDetailsGraphDto {
	
	private String production;
	
	private String target;
	
	private String achievement;
	
	private String efficient;
	
	private String alterParcentage;
	
	private String rejectParcentage;
	
	private String defectParcentage;
	
	private String numberOfLineProduction;

}
