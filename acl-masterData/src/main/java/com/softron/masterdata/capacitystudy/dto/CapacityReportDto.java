package com.softron.masterdata.capacitystudy.dto;

import java.sql.Time;
import java.util.List;

import lombok.Data;

@Data
public class CapacityReportDto {
	
	private String operation;
	
	private String machinetype;
	
	private int operationCapacity;
	
	List<OperatorDataDto> operatorDataDto;
	
}
