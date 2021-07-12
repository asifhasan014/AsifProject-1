package com.softron.production.dto;

import lombok.Data;

@Data
public class ProductionDto {
	
	private Long id;
	
//	private Double dayTarget;
//	
//	private Double dayProduction;
//	
//	private Double nowTarget;
//
//	private Double nowProduction;
//	
//	private Double thisHourTarget;
//	
//	private Double thisHourProduction;
//	
//	private Double activedQty=100.0;
//	
//	private Double planQty=1000.0;
//	
//	private Double productivity;
//	
//	private Double Efficiency;
//	
//	private String StyleName;
	
	private int HrTarget;
	
	private int HrProduction;
	
	private int lineTarget;
	
	private int CumalativeTarget;
	
	private double CumalativeTargetDoubleValue;
	
	private int CumalativeProduction;
	
	private int DayTarget;
	
	private double dayTargetDoubleValue;
	
	private int Production;
	
	
	
	private int Operator;
	
	private int Helper;
	
	private String StyleName;
	
	private double CumalativeWorkingHr;
	
	private int DayWorkingHr;
	
	private double productivity;
	
	private double produceMinute;
	
	private double Efficiency;

	private String company;
	private String line;
	private String buyer;
	private String styleName;
	private String smv;
	private String orderNo;
	private String productionTransactionDate;
	private String productionTransactionHour;
	private String quantity;
	private double balancingGap;
}
