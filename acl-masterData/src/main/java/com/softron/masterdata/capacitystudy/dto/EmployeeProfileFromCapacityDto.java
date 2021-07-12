package com.softron.masterdata.capacitystudy.dto;

import lombok.Data;

@Data
public class EmployeeProfileFromCapacityDto {

	private Long operationId;
	
	private String operation;
	
	private Long styleId;
	
	private String styleName;
	
	private float averageCapacity;
	
	private float smv;
	
	private float efficiency;
	
}
