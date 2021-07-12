package com.softron.quality.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.WorkProcessDto;

import lombok.Data;

@Data
public class QualityTypeDto {

	
	private Long id;
	
	private String name;
	
	@JsonIgnoreProperties({"operation","employee","defect","qualityTransaction","qualityType"})
	private WorkProcessDto workProcess;
	
	private Integer isOperationWise;
	
	private Integer sampleSize;
	
//	@JsonIgnoreProperties({"section","style","qualityType","workProcess","operation"})
//	private List<QualityTransactionDto> qualityTransaction;
	
	private Integer type;
}
