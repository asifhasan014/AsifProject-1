package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.quality.dto.VarienceDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Style;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class OrderEntityDto {

	private Long id;

	private String name;

	//@JsonIgnoreProperties({"company","organization","operationBreakDown"})
	@JsonIgnoreProperties({"company","organization"})
	private StyleDto style;

	private long quantity;
	
	private String proxyCardNo;

	@JsonIgnoreProperties("organization")
	private CustomerDto customer;
	
//	@JsonIgnoreProperties({"client","plant","item"})
//	private CompanyDto company;

	private String lineName;
	
	private String status;

//	private List<QualityTransaction> qualityTransaction;
	
    @JsonIgnoreProperties("orderEntity")
	private List<VarienceDto> varience;
    
    @JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
    
//  @JsonIgnoreProperties("orderEntity")
//	private List<OperationMachineDto> operationMachine;

}
