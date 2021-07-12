package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softron.quality.dto.QualityTransactionDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Data

public class SectionDto {
	private Long id;

	private String name;
	
	private String text;
	
	private String proxyCardNo;
	
	@JsonIgnoreProperties({"company","section","capacityStudy","customer"})
	private Organization organization;
	
	/*
	 * @OneToMany(cascade = CascadeType.ALL,mappedBy = "section") private
	 * List<SubSection> subSection;
	 */
	@JsonIgnoreProperties({"parent"})
	private SectionDto parent;
    
	@JsonIgnoreProperties({"parent", "subSection"}) 
	private List<SectionDto> subSection;
	
	@JsonIgnoreProperties({"parent", "subSection"}) 
	private List<SectionDto> items;
//	
//	
//	private List<QualityTransactionDto> qualityTransaction;
//	private List<OperationMachineDtoDto> operationMachineDto;
	
	private String sectionHead;

	private long overhead;

	private double standardCost;

}
