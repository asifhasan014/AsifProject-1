package com.softron.schema.qualitymodule.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import lombok.Data;


@Entity
@Data
public class QualityType extends BaseModel{

	private String name;
	
	@ManyToOne
	@JoinColumn(name = "WORKPROCESS_ID", referencedColumnName = "id")
	private WorkProcess workProcess;
	
	private Boolean isOperationWise;
	
	private long sampleSize;
	
	private Integer type;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qualityType")
	private List<QualityTransaction> qualityTransaction;
	
}
