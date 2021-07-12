package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Entity
@Data
public class OperationMachine extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id",nullable=true)
	private Employee employee;

	private Long machineId;

	@ManyToOne
	@JoinColumn(name = "OPERATIONBREAKDOWN_ID", referencedColumnName = "id",nullable=true)
	private OperationBreakDown operationBreakDown;
	
//	@ManyToOne
//	@JoinColumn(name = "SECTION_ID", referencedColumnName = "id",nullable=true)
//	private Section section;
	
	@ManyToOne
	@JoinColumn(name = "ORDERENTITY_ID", referencedColumnName = "id",nullable=true)
	private OrderEntity orderEntity;
	
	@ManyToOne
	@JoinColumn(name = "ORG_ID", referencedColumnName = "id",nullable=true)
	private Organization organization;
	
	
}
