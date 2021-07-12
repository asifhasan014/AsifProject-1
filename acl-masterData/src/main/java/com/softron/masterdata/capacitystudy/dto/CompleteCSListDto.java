package com.softron.masterdata.capacitystudy.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.dto.UserDto;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class CompleteCSListDto {
	
	private Long id;

	private String studyDate;

	private String capacityStudyNo;

	private String Description;

	private int runningDay;

	private int target;

	private int currentlineproduction;

	private LocalDateTime layoutStartDate;
	
	private String layoutStartDay;

	private double tacct;

	private int potentialPcs;

	private double productivityGap;

	private String status;
	
	private String sharingMode;
	
	private String code;
	
	private List<CapacityStudyDetailsDto> capacityStudyDetails;
	
	private UserDto sharedUser;

	private OrderEntityDto orderEntity;
	
	private Organization organization;
}
