package com.softron.masterdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class ClientDto {

	private Long id;

	private String name;

	private String address;

	private String responsiblePerson;

	@JsonIgnoreProperties({"client","organization"})
	private UserDto user;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
	
  // @JsonIgnore
	//@JsonIgnoreProperties({"client", "plant"})
  // private List<CompanyDto> company;

}
