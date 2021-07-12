package com.softron.masterdata.capacitystudy.dto;

import lombok.Data;

@Data
public class CapacityGraphDto {

	private String operation;
	
	private String operatorName;
	
	private int capacity;
	
	private String machineName;
}
