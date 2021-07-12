package com.softron.masterdata.capacitystudy.dto;

import lombok.Data;

@Data
public class OperationAnalysisCSDto {

	private Long operationId;
	
	private String operationName;
	
	private String styleName;
	
	private Long styleId;
	
	private String employeeCode;
	
	private String operatorName;
	
	private String employeeName;
	
	private double operationSmv;
	
	private double cycleTime;
	
	private double operatorEfficiency;
	
	private int numberOfData;
	
}
