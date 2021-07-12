package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Entity
@Data
public class Customer extends BaseModel {
	
	private String name;
	
	private String address;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	private List<OrderEntity> orderEntity;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;

}
