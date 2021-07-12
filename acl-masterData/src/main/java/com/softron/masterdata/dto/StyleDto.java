package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class StyleDto {

	private Long id;

	private String name;

	private String smallName;

	private float smv;

	private float manPower;

	private Integer criticalIndex;
  
	@JsonIgnoreProperties("style")
	private List<OperationBreakDownDto> operationBreakDown;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
	
	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ItemDto item;


}
