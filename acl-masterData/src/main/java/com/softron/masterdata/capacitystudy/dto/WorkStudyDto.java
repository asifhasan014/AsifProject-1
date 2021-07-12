package com.softron.masterdata.capacitystudy.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class WorkStudyDto {
	
	private Long id;
	
	private String description;
	
	private float minnuteValue;
	
	private String code;
	
	//@JsonManagedReference
	@JsonIgnoreProperties({"workStudy","operationMachine","operationBreakDown","capacityStudy"})
	private CapacityStudyDetailsDto capacityStudyDetails;
	
	//@JsonBackReference
	@JsonIgnoreProperties("workStudy")
	private List<LabHistoryDto> labHistory;

}
