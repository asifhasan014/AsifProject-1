package com.softron.schema.qualitymodule.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Operation;

import lombok.Data;

@Entity
@Data
public class QualityDefect extends BaseModel {
	
	@ManyToOne
	@JoinColumn(name = "DEFECT_ID", referencedColumnName = "id")
	private Defect defect;
	
	@ManyToOne
	@JoinColumn(name = "OPERATION_ID", referencedColumnName = "id")
	private Operation operation;
	
	@ManyToOne
	@JoinColumn(name = "QUALITYTRANSACTION_ID", referencedColumnName = "id")
	private  QualityTransaction qualityTransaction;
	
	@ManyToOne
	@JoinColumn(name = "ORG_ID", referencedColumnName = "id")
    private Organization organization; 
	

	
}
