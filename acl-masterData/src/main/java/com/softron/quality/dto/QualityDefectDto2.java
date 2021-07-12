package com.softron.quality.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class QualityDefectDto2 {
	
	
	private Long id;
	
	@JsonIgnoreProperties("workProcess")
	private DefectDto defect;
    
	@JsonIgnoreProperties("workProcess")
	private OperationDto operation;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower" })
	private Organization organization;
    
}
