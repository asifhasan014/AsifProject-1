package com.softron.schema.admin.entity.masterdata;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
import com.softron.schema.qualitymodule.entity.Varience;

import lombok.Data;

@Entity
@Data
public class OrderEntity extends BaseModel{
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="STYLE_ID",referencedColumnName = "id")
	private Style style;
	
	private long quantity;
	
	private String proxyCardNo;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID",referencedColumnName = "id")
	private Customer customer;
	
//	@ManyToOne
//	@JoinColumn(name="COMPANY_ID",referencedColumnName = "id")
//	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderEntity")
	private List<QualityTransaction> qualityTransaction;
	
//	@OneToMany(mappedBy = "orderEntity")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderEntity")
	private List<Varience> varience;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderEntity")
	private List<OperationMachine> operationMachine;
	
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;
	

}
