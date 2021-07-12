package com.softron.masterdata.dto;

import java.util.List;

import lombok.Data;

@Data
public class GenericIdAndText<T> {
	
	private Long id;
	
	private String text;
	
	private List<GenericDto> item;

}
