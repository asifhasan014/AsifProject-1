package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.capacitystudy.entity.CapacityStudyDetails;

import lombok.Data;

@Entity
@Data
public class OperationBreakDown extends BaseModel{
	
	@ManyToOne
	@JoinColumn(name="STYLE_ID",referencedColumnName = "id")
	private Style style;
	
	private float allowance;
	
	@ManyToOne
	@JoinColumn(name="OPERATION_ID",referencedColumnName = "id")
	private Operation operation;
	
	private float smv;
	
	private float machineQuantity;
	
	@ManyToOne
	@JoinColumn(name="MACHINETYPE_ID",referencedColumnName = "id")
	private MachineType machineType;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operationBreakDown")
	private List<OperationMachine> operationMachine;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operationBreakDown")
	private List<CapacityStudyDetails> capacityStudyDetails;
	
	

}
