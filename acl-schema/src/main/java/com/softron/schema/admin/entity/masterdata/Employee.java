package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.capacitystudy.entity.CapacityStudyDetails;
import com.softron.schema.qualitymodule.entity.QualityDefect;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Entity
@Data
public class Employee extends BaseModel{
	
	private String name;
	
	private String surName;
	
	private String employeeCode;
	
	private String designation;
	
	private String grade;
	
	//private String type;
	
	private int employeeType;
	
	private String proxyCardNo;
	
	private int tlsStatus;
	
	private String line;
	
	@ManyToOne
	@JoinColumn(name="WORKPROCESS_ID",referencedColumnName = "id")
	private WorkProcess workProcess;
	
//	@ManyToOne
//	@JoinColumn(name="COMPANY_ID",referencedColumnName = "id")
//	private Company company;
	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",referencedColumnName = "id")
	private Department department;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
	private List<QualityTransaction> qualityTransaction;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
	private List<OperationMachine> OperationMachine;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
	private List<CapacityStudyDetails> capacityStudyDetails;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;

}
