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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import lombok.Data;

@Entity
@Data
public class Client extends BaseModel{

	private String name;

	private String address;

	private String responsiblePerson;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
//	private List<Company> company;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID",referencedColumnName = "id")
	private Organization organization;

}
