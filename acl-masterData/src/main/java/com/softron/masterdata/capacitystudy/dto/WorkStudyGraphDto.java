package com.softron.masterdata.capacitystudy.dto;

import java.util.List;
import lombok.Data;

@Data
public class WorkStudyGraphDto {

	private String buyerName;
	
	private String styleName;
	
	private String lineInfo;
	
	private String date;
	
	private int daysRun;
	
	private int graphInfo;
	
	private long operator;
	
	private long helper;
	
	private long ttlMp;
	
	private float smv;
	
	private int currentPcs;
	
	private String studiedBy;
	
	private float cycleTime;
	
	private double basicPitchTime;
	
	private long workerPotential;
	
	private int productivityGap;
	
}
