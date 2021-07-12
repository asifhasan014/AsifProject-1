package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Style;

import lombok.Data;

@Data
public class CustomerDto {
	
	private Long id;

	private String name;

	private String address;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;

	
}
