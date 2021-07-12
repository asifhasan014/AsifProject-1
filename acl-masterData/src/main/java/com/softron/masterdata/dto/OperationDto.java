package com.softron.masterdata.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softron.common.utils.UrlConstants.QualityDefect;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.quality.dto.QualityTransactionDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Data
public class OperationDto {
	
	private Long id;
	
//	private Boolean active;

	private String name;
	
	private String processCode;

	private float smv;

	private String type;
	
	private String proxyCardNo;

	private Integer criticalIndex;

	@JsonIgnoreProperties({"organization"})
	private WorkProcessDto workProcess;
	
//	@JsonIgnoreProperties({"client","plant","item"})
//	private CompanyDto company;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
//
//	@JsonIgnoreProperties({ "style", "operation", "machineType" })
//	private List<OperationBreakDownDto> operationBreakDown;
//
//
//	private List<QualityTransactionDto> qualityTransaction;
//
//	private List<QualityDefectDto> qualityDefect;

}
