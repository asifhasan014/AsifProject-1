package com.softron.schema.qualitymodule.entity;


import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.entity.masterdata.WorkProcess;

import lombok.Data;

@Entity
@Data
public class QualityTransaction extends BaseModel{
	
//	@ManyToOne
//	@JoinColumn(name = "SECTION_ID", referencedColumnName = "id")
//    private Section section; 
    
	@ManyToOne
	@JoinColumn(name = "STYLE_ID", referencedColumnName = "id")
    private Style style;
	
	@ManyToOne
	@JoinColumn(name = "QUALITYTYPE_ID", referencedColumnName = "id")
    private QualityType qualityType;
    
	@ManyToOne
	@JoinColumn(name = "WORKPROCESS_ID", referencedColumnName = "id")
    private WorkProcess workProcess;
    
	@ManyToOne
	@JoinColumn(name = "OPERATION_ID", referencedColumnName = "id",nullable = true)
    private Operation operation;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id",nullable = true)
    private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "ORDERENTITY_ID", referencedColumnName = "id",nullable = true)
    private OrderEntity orderEntity;
	
	@ManyToOne
	@JoinColumn(name = "VARIENCE_ID", referencedColumnName = "id",nullable = true)
    private Varience varience;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qualityTransaction")
	private List<QualityDefect> qualityDefect;
	
	@ManyToOne
	@JoinColumn(name = "ORG_ID", referencedColumnName = "id")
    private Organization organization; 
    
    
    private long sampleSize;
    
    private String checkOutput;
    
    private int defectiveItem;
    
    //private Date date;
    
    private Time elapsedTime;
    
    private Date dateTime;
    
    private String createdUser;
	

}
