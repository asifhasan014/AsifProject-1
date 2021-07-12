package com.softron.masterdata.capacitystudy.dto;

import java.sql.Time;

import lombok.Data;

@Data
public class OperatorDataDto {

	private String operator;
	
	private float allowance;
	
	private int capacity;
	
	private float average;
}
