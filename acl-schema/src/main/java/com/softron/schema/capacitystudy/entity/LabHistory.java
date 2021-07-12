package com.softron.schema.capacitystudy.entity;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.softron.schema.admin.entity.masterdata.BaseModel;

import lombok.Data;

@Entity
@Data
public class LabHistory extends BaseModel{
	
	private Time labTime;
	
	private float minuteValue;
	
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "WORKSTUDY_ID", referencedColumnName = "id",nullable=true)
	private WorkStudy workStudy;

}

