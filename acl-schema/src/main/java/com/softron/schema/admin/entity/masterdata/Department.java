package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Department extends BaseModel{
	
	private String name;
	
	private String head;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "department")
	private List<Employee> employee;

}
