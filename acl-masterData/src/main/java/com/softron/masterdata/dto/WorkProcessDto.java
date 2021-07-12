package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softron.quality.dto.QualityTransactionDto;
import com.softron.quality.dto.QualityTypeDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
import com.softron.schema.qualitymodule.entity.QualityType;

import lombok.Data;

@Data
public class WorkProcessDto {
	private Long id;

	private String name;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;

	
//	private Organization organization;

//	@JsonIgnoreProperties("workProcess")
//	private List<OperationDto> operation;
//	
//	@JsonIgnoreProperties({ "department", "workProcess" })
//	private List<EmployeeDto> employee;
//	
//	@JsonIgnoreProperties("workProcess")
//	private List<DefectDto> defect;
//	
// 
//	private List<QualityTransactionDto> qualityTransaction;
//	
//	//@JsonIgnoreProperties("workProcess") 
//	private List<QualityTypeDto> qualityType;

}


