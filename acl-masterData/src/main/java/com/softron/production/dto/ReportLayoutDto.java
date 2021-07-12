package com.softron.production.dto;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.UserDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;

import lombok.Data;

@Data
public class ReportLayoutDto {

	private Long id;

	private String name;

	private String type;

	private String rowFields;

	private String columnFields;

	private String dataFields;

	private String filterFields;

	private String link;

	private String viewType;

//	private Date fromDate;
//
//	private Date toDate;

	private String fromDateString;

	private String toDateString;

	private boolean showGraph;

	private UserDto user;

	private String timeUnit;

	private String category;

	private String organizationIdList;
	
	private String orderIdList;

	private String dateRange;

//	@JsonIgnoreProperties({ "section", "workProcess", "employee", "operation", "defect", "qualityTransaction", "client",
//			"orderEntity", "style", "userEntity", "operationMachine", "targetAndManpower" })
//	private Organization organization;

}
