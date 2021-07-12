package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class CompanyDto {
	private Long id;
	
	private String name;
	
	private String text;
	
	private String companyCode;

	private String address;
}
