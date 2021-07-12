package com.softron.schema.admin.entity;

import com.softron.schema.admin.entity.masterdata.BaseModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
public class Settings extends BaseModel{

	private Integer workingHours;

	/*@OneToOne(mappedBy = "settings")
	private Organization organization;*/
}
