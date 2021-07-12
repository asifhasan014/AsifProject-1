package com.softron.masterdata.parameter_selection_pannel.dto;

import java.util.List;

import com.softron.masterdata.dto.OrderEntityDto;

import lombok.Data;

@Data
public class OrderWiseStyleDto {
	
	private Long id;

	private String name;
	
	private List<OrderEntityDto> order;

}
