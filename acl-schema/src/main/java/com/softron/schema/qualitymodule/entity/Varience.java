package com.softron.schema.qualitymodule.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import lombok.Data;

@Entity
@Data
public class Varience extends BaseModel{

	private String color;

	private String size;

	private long orderQuantity;

	@ManyToOne
	@JoinColumn(name = "ORDERENTITY_ID", referencedColumnName = "id", nullable = true)
	private OrderEntity orderEntity;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "varience")
	private List<QualityTransaction> qualityTransaction;

}
