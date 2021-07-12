package com.softron.quality.dto;

import java.util.List;

import lombok.Data;

@Data
public class DefectAnalysisReportDto {
	
	private String date;
	private String hour;
	private String client;
	private String company;
	private String plant;
	private String section;
	private String subSection;	
	private String rootPath;
	private List<String> organizationList;
	
	private String style;
	private String buyer;
	private String orderItem;
	private String operation;
	private String employee;
	private String qualityCheckType;
	private String defect;
	private int quantity;
}
