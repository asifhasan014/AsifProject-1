package com.softron.production.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductionDboardQcDto {

    private int helper;
	
	private int HrProduction;
	
	private int lineTarget;
	
	private int CumalativeTarget;
	
	private int CumalativeProduction;
	
	private int DayTarget;
	
	private int operator;
	
	private double Efficiency;
	
	private double productivity;
	
	private double balancingGap;
	
	private double produceMinute;
	
	private List<String> organizationList; 
}
