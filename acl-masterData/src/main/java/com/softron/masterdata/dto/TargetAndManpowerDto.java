package com.softron.masterdata.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class TargetAndManpowerDto {

	private Long id;
	
	private long productiontarget;
	
	private long numberOfOperator;
	
	private long numberOfHelper;
	
	private Date date;
	
	private String dateString;
	
	private float workingHr;
	
//	@JsonIgnoreProperties({"plant","parent","subSection","items"})
//	private SectionDto section;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
	
}
