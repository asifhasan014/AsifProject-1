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

import lombok.Data;

@Entity
@Data
public class Defect extends BaseModel{
	
	
	private String name;
	
	private String type;
	
	@ManyToOne
	@JoinColumn(name="WORKPROCESS_ID",referencedColumnName = "id")
	private WorkProcess workProcess;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "defect")
	private List<QualityDefect> qualityDefect;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;

}
