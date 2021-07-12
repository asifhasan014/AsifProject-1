package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Section extends BaseModel{
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="PLANT_ID",referencedColumnName ="id")
	private Plant plant;

	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName ="id")
	private Organization organization;
	
	/*
	 * @OneToMany(cascade = CascadeType.ALL,mappedBy = "section") private
	 * List<SubSection> subSection;
	 */
	//@Column(nullable=true)
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="parent_id",nullable=true)
	private Section parent;

	@OneToMany(mappedBy="parent")
	private List<Section> subSection;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
//	private List<QualityTransaction> qualityTransaction;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
//	private List<OperationMachine> operationMachine;
//	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
//	private List<TargetAndManpower> targetAndManpower;
	
	private String sectionHead;
	
	private long overhead;
	
	private double standardCost;
	
	private String proxyCardNo;
	

}
