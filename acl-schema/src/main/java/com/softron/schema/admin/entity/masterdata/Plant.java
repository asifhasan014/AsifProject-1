package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Plant extends BaseModel{

	
private String name;
	
	private String address;
	
	private String type;
	
	@ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "id")
	private Company company;
	
//	@OneToMany(cascade = CascadeType.ALL,mappedBy ="plant")
//	private List<Section> section;
	
	private String plantHead;

}
