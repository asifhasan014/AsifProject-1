package com.softron.masterdata.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class EmployeeDto {
	private Long id;

	private String name;

	private String surName;

	private String employeeCode;

	private String designation;

	private String grade;

	private int employeeType;

	private String proxyCardNo;
	
	private int tlsStatus;
	
	private String line;

	@JsonIgnoreProperties({ "organization" })
	private WorkProcessDto workProcess;

	private DepartmentDto department;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})

	private Organization organization;
	
	
	private String employeeImage;


}
