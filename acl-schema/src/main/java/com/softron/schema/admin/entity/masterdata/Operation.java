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
import com.softron.schema.qualitymodule.entity.QualityDefect;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Entity
@Data
public class Operation extends BaseModel{
	
	
	private String name;
	
	private float smv;
	
	private String type;
	
	private String proxyCardNo;
	
	private String processCode;
	
	private Integer criticalIndex;
	
	@ManyToOne
	@JoinColumn(name="WORKPROCESS_ID",referencedColumnName = "id")
	private WorkProcess workProcess;
	
//	@ManyToOne
//	@JoinColumn(name="COMPANY_ID",referencedColumnName = "id")
//	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "operation")
	private List<OperationBreakDown> operationBreakDown;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operation")
	private List<QualityDefect> qualityDefect;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operation")
	private List<QualityTransaction> qualityTransaction;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;

}
