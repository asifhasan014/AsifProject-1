package com.softron.quality.dto;

import java.util.List;

import lombok.Data;

@Data
public class QcPassReportDto {
	
	private String Date;
	private String hour;
	private String client;
	private String company;
	private String plant;
	private String section;
	private String subSection;
	private String rootPath;
	private List<String> organizationList;
	
	private String size;
	private String color;
	private int totalCheck;
	private int qcPass;
	private double qcPassPercent;
	private int totalAlter;
	private double alterPercent;
	private int reject;
	private double rejectPercent;
	private int defect;
	private double dhu;

}
