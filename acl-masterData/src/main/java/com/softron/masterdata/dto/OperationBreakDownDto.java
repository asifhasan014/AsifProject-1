package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.Style;

import lombok.Data;

@Data
public class OperationBreakDownDto {
	
	private Long id;
	
	@JsonIgnoreProperties({"customer","operationBreakDown","organization"})
	private StyleDto style;
	
	@JsonIgnoreProperties({"workProcess"})
	private OperationDto operation;
	
	private float smv;
	
	private float allowance;
	
	private float machineQuantity;
	
	//@JsonIgnoreProperties("operationBreakDown")
	private MachineTypeDto machineType;
	
	@JsonIgnoreProperties("operationBreakDown")
	private List<OperationMachineDto> operationMachine;
}
