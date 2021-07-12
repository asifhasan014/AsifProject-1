package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class MachineType extends BaseModel{
	
	private String smallName;
	
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "machineType")
	private List<OperationBreakDown> operationBreakDown;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "machineType")
	private List<Machine> machine;
}
