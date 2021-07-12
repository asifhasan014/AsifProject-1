package com.softron.masterdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class MachineDto {

	private Long id;
	
	private String code;
	
	private String description;
	
	private MachineTypeDto machineType;
	
	@JsonIgnoreProperties({"organization"})
	private DeviceDto device;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
}
