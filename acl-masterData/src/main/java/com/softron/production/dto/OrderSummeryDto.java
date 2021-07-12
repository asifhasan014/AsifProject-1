package com.softron.production.dto;

import lombok.Data;

@Data
public class OrderSummeryDto {
	
	private Long orderId;

	private String buyer;
	
	private String style;
	
	private String order;
	
	private String orderStutus;
	
	private String quantity;
	
	private String productionStartDate;
	
	private String runningProductionLine;
	
	private String lineInvole;
	
	private String totalProduction;
	
	private String alter;
	
	private String alterPercentage;
	
	private String reject;
	
	private String rejectPercentage;
	
	private String defect;
	
	private String dhu;
	
	private String smv;
	
	private String avgHrTarget;
	
	private String avgHrProduction;
	
	private String avgAchivement;
	
	private String avgMp;
	
	private String efficiency;
	
	private String numberOfOperation;
	
	private String lastDayProduction;
	
	private String currentDayProduction;
	
	private String lastHrProduction;
	
	private String currentHrProduction;
}
