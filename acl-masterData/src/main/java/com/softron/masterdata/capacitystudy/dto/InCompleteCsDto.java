package com.softron.masterdata.capacitystudy.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.dto.UserDto;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class InCompleteCsDto {

	private Long id;

	private String studyDate;

	private String capacityStudyNo;

	private String Description;

	private Integer runningDay;

	private Integer target;

	private Integer currentlineproduction;

//	private LocalDateTime layoutStartDate;
	
	private String layoutStartDay;

	private Double tacct;

	private Integer potentialPcs;

	private Double productivityGap;

	private String status;
	
	private String sharingMode;
	
	private String category;

	private String performanceLine;

	private String buyer;

	private StyleDto style;

	private String studyBy;

	private String percentageOfComplete;
	
	private String sharedUser;

	@JsonIgnoreProperties({ "operationBreakDown", "employee" })
	private List<CapacityStudyDetailsDto> capacityStudyDetails;

	@JsonIgnoreProperties({ "style", "customer", "varience" })
	private OrderEntityDto orderEntity;

	@JsonIgnoreProperties({ "section", "workProcess", "employee", "operation", "defect", "qualityTransaction", "client",
			"orderEntity", "style", "userEntity", "operationMachine", "targetAndManpower", "capacityStudy" })
	private Organization organization;

}
