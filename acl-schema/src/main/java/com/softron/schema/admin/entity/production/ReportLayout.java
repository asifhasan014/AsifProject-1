package com.softron.schema.admin.entity.production;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.BaseModel;

import lombok.Data;

@Entity
@Data
public class ReportLayout extends BaseModel{

	private String name;
	
	private String type;
	
	private String rowFields;
	
	private String columnFields;
	
	private String dataFields;
	
	private String filterFields;
	
	private String link;
	
	private String viewType;
	
	private Date fromDate;
	
	private Date toDate;
	
	private boolean showGraph;
	
	private String timeUnit;
	
	private String category;
	
	private String organizationIdList;
	
	private String orderIdList;
	
	private String dateRange;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
//	@ManyToOne
//	@JoinColumn(name="ORG_ID",referencedColumnName = "id",nullable = true)
//	private Organization organization;

	
}
