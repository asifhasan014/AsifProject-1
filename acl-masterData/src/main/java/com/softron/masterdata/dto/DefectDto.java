package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softron.common.utils.UrlConstants.QualityDefect;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;
@Data
public class DefectDto {
	private Long id;
	
	private String name;

	private String type;
	
	@JsonIgnoreProperties({ "organization" })
	private WorkProcessDto workProcess;
	
	@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;
	
//	private List<QualityDefectDto> qualityDefect;

}
