package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class PlantDto {
	private Long id;

	private String name;
	
	private String text;

	private String address;

	private String type;
	
	@JsonIgnoreProperties({"client","plant"})
	//@JsonIgnore
	private CompanyDto company;
    
//	//@JsonManagedReference("plantsection")
//	//@JsonIgnore
	
//	@JsonIgnoreProperties({ "plant", "parent" })
//	private List<SectionDto> section;
//	
//	@JsonIgnoreProperties({ "plant", "parent" })
//	private List<SectionDto> items;

	private String plantHead;

}
