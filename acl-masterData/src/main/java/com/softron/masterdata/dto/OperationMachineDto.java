package com.softron.masterdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.common.businessobjects.Response;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;

import lombok.Data;

@Data
public class OperationMachineDto {
	
	private Long id;

	@JsonIgnoreProperties({"workProcess","department","company","organization"})
	private EmployeeDto employee; // need section ID, NAME
	private Long machineId;
	
	//@JsonIgnore
	@JsonIgnoreProperties({"style","operation","machineType","operationMachine"})
	private OperationBreakDownDto operationBreakDown; // need section ID, NAME
	
//	@JsonIgnoreProperties({"plant","parent","subSection","organization"})
//	private SectionDto section; // need section ID, NAME
	
	//private int TLSstatus;
	
	@JsonIgnoreProperties({"style","customer","varience","company","organization"})
	private OrderEntityDto orderEntity;

	private Long orderEntityId;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
	
	private String employeeImage;
	
}
