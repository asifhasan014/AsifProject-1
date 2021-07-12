package com.softron.masterdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class DepartmentDto {
	private Long id;
	
	private String name;

	private String head;
	
//	//@JsonIgnoreProperties("department")
//	@JsonIgnoreProperties({ "department", "workProcess" })
//	private List<EmployeeDto> employee;

}
