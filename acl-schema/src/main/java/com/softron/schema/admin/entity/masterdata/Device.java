package com.softron.schema.admin.entity.masterdata;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Entity
@Data
public class Device extends BaseModel{

	private String code;

	@Column(name="macAddress", unique=true)
	private String macAddress;
	
	@OneToOne(mappedBy = "device")
	private Machine machine;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;
}
