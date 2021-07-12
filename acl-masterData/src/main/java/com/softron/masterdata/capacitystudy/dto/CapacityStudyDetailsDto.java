package com.softron.masterdata.capacitystudy.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.dto.OperationMachineDto;

import lombok.Data;

@Data
public class CapacityStudyDetailsDto {

	private Long id;

	private Long operationBreakDownId;

	private float cycleTime;

	private float allowance;

	private int capacity;

	private OperationMachineDto operationMachine;
	
	private EmployeeDto employee;
	
	private String code;
	
	private OperationBreakDownDto operationBreakDown;

	//@JsonBackReference
	private List<WorkStudyDto> workStudy;

	@JsonIgnoreProperties({"capacityStudyDetails","orderEntity","organization"})
	private CapacityStudyDto capacityStudy;
}