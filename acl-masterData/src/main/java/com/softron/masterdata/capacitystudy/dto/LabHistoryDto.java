package com.softron.masterdata.capacitystudy.dto;


import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LabHistoryDto {
	
	private Long id;

	private Time labTime;

	private float minuteValue;
	
	private String code;

	//@JsonManagedReference
//	@JsonIgnoreProperties({"capacityStudyDetails","labHistory"})
	@JsonIgnore
	private WorkStudyDto workStudy;
}
