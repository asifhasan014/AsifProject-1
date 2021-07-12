package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Entity
@Data
public class Style extends BaseModel {
	
	private String name;
	
	private String smallName;
	
//	@ManyToOne
//	@JoinColumn(name="CUSTOMER_ID",referencedColumnName = "id")
//	private Customer customer;
	
	private float smv;
	
	private float manPower;
	
	private Integer criticalIndex;
	
//	@ManyToOne
//	@JoinColumn(name="COMPANY_ID",referencedColumnName = "id")
//	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "style")
	private List<OrderEntity> orderEntity;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "style")
	private List<OperationBreakDown> operationBreakDown;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "style")
	private List<QualityTransaction> qualityTransaction;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID", referencedColumnName = "id", nullable = true)
	private Item item;

}
