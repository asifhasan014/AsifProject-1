package com.softron.schema.capacitystudy.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;

import lombok.Data;

@Entity
@Data
public class CapacityStudyDetails extends BaseModel{
	
	private float cycleTime;
	
	private int capacity;
	
	private String code;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "OPERATIONMACHINE_ID", referencedColumnName = "id",nullable=true)
	private OperationMachine operationMachine;
	
	@ManyToOne
	@JoinColumn(name = "CAPACITYSTUDY_ID", referencedColumnName = "id",nullable=true)
	private CapacityStudy capacityStudy;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id",nullable=true)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "OPERATIONBREAKDOWN_ID", referencedColumnName = "id",nullable=true)
	private OperationBreakDown operationBreakDown;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "capacityStudyDetails")
	private List<WorkStudy> workStudy;
	
	


}
