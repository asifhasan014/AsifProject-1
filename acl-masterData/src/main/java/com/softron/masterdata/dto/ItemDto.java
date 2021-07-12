package com.softron.masterdata.dto;

import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class ItemDto {
	
	private Long id;

	private String name;
	
	private Organization organization;
}
