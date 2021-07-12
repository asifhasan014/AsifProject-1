package com.softron.production.dto;

import lombok.Data;

@Data
public class ProductionChartDto {

	private String operationName;
	private String hour;
	private double productionQuantity;
}
