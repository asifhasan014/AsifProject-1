package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;

import lombok.Data;

@Data
public class MachineTypeDto {

	private Long id;

	private String smallName;

	private String description;

//	private List<OperationBreakDownDto> operationBreakDown;

}
