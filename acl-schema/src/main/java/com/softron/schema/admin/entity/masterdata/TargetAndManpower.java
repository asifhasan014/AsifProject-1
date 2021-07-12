package com.softron.schema.admin.entity.masterdata;


import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
import lombok.Data;

@Entity
@Data
public class TargetAndManpower extends BaseModel{

	private long productiontarget;
	
	private long numberOfOperator;
	
	private long numberOfHelper;
	
	private Date date;
	
	private float workingHr; 
	
//	@ManyToOne
//	@JoinColumn(name="SECTION_ID",referencedColumnName = "id")
//	private Section section;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;
	
	
	
}
