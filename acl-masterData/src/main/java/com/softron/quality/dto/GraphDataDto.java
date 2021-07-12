package com.softron.quality.dto;

import java.util.List;

import lombok.Data;

@Data
public class GraphDataDto {
	
private String organization;
private String rootPath;
private String time;
private String type;
private String value;
private List<String> organizationList;


private String alterAverage;
private String rejctAverage;
private String dhuAverage;

private String defectName;
private String operationName;
private String production;
private String productionMinute;
private String totalcheck;
private String qcPass;
private String defectQuantity;
private String efficiency;
private String dhu;
private String qcDate;
private String quality;
private String style;

private String sectionName;

}
