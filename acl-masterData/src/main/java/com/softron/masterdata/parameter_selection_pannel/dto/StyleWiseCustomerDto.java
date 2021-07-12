package com.softron.masterdata.parameter_selection_pannel.dto;

import java.util.List;

import lombok.Data;

@Data
public class StyleWiseCustomerDto {
	
	private Long id;

	private String name;
	
	private List<OrderWiseStyleDto> style;
	
}
