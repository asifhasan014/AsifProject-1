package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Entity
@Data
public class Machine extends BaseModel{

	@Column(name="code", unique=true)
	private String code;
	
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "MACHINETYPE_ID", referencedColumnName = "id")
	private MachineType machineType;

	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;
}
