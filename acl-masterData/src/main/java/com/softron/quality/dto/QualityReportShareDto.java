package com.softron.quality.dto;

import java.util.Date;

import lombok.Data;

@Data
public class QualityReportShareDto {
	
	private String column;
	private String row;
	private String dataField;
	private String viewType;
	private Date startDate;
	private Date endDate;
	
	private String QDate;
	private String client;
	private String company;
	private String plant;
	private String section;
	private String subSection;
	
	private String totalCheck;
	private String qcPass;
	private String totalAlter;
	private String reject;
	private String defect;
	
	private String qcPassPercent;
	private String alterPercent;
	private String rejectPercent;
	private String dhu;

}
