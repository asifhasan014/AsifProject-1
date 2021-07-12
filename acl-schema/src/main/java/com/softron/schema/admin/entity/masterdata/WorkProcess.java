package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
import com.softron.schema.qualitymodule.entity.QualityType;

import lombok.Data;

@Entity
@Data
public class WorkProcess extends BaseModel{
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "workProcess")
    private List<Operation> operation;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "workProcess")
	private List<Employee> employee;
	
	@OneToMany(mappedBy = "workProcess")
	private List<Defect> defect;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "workProcess")
	private List<QualityTransaction> qualityTransaction;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "workProcess")
	private List<QualityType> qualityType;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;


}
