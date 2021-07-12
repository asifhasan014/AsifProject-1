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
public class Item extends BaseModel{

	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
	private List<Style> style;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;
}
