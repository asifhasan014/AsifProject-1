package com.softron.schema.capacitystudy.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.masterdata.BaseModel;

import lombok.Data;

@Entity
@Data
public class WorkStudy extends BaseModel{

	private String description;
	
	private float minnuteValue;
	
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "CAPACITYSTUDYDETAILS_ID", referencedColumnName = "id",nullable=true)
	private CapacityStudyDetails capacityStudyDetails;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "workStudy")
	private List<LabHistory> labHistory;
}
