package com.softron.masterdata.home.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class HomeDto {

	private Object yearProduction;
	
	private String yearProductionBar;
	
	private  String customerName;
	
	private String thisyearCustomerProduction;
	
	private String thismonthProduction;
	
	private String monthDate;
	
	private String dayProduction;
	
	private String efficiency;
	
	private String Dhu;
	
	private List<String> organizationList; 
}
