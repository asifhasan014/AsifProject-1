package com.softron.quality.dto;

import lombok.Data;

@Data
public class QualityReportDto {
	
	private String date;
	private String hour;
	private String client;
	private String company;
	private String plant;
	private String section;
	private String subSection;
	
	private String totalCheck;
	private String totalOk;
	private String percentageOk;
	private String totalAlter;
	private String percentageAlter;
	private String totalReject;
	private String percentageReject;
	private String totalDefect;
	private String percentageDefect;
	
	
	private String style;
	private String buyer;
	private String orderItem;
	private String operation;
	private String employee;
	private String qualityCheckType;
	private String defect;
	private String quantity;
	

}
