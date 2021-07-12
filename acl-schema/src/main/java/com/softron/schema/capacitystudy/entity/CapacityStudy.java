package com.softron.schema.capacitystudy.entity;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.OrderEntity;

import lombok.Data;

@Entity
@Data
public class CapacityStudy extends BaseModel {

	private String capacityStudyNo;
	
	private String Description;
	
	private int runningDay;
	
	private int target;
	
	private int currentlineproduction;
	
	private LocalDateTime layoutStartDay;
	
	private double tacct;
	
	private int potentialPcs;
	
	private double productivityGap;
	
	private String status;
	
	private String userName;
	
	private String sharingMode;
	
	private String code;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "capacityStudy")
	private List<CapacityStudyDetails> capacityStudyDetails;

//	@ManyToOne
//	@JoinColumn(name = "SECTION_ID", referencedColumnName = "id", nullable = true)
//	private Section section;

	@ManyToOne
	@JoinColumn(name = "ORDERENTITY_ID", referencedColumnName = "id", nullable = true)
	private OrderEntity orderEntity;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHAREDUSER_ID",nullable = true)
    private UserEntity sharedUser;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;

}