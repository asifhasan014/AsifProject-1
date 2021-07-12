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

import com.softron.schema.admin.entity.UserEntity;

import lombok.Data;

@Entity
@Data
public class Company extends BaseModel{
	private String name;
	
	private String companyCode;
	
	private String address;
	
//    @ManyToOne
//    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id")
//	private Client client;
//    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
//    private List<Plant> plant;
//    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
//    private List<Employee> employee;
//    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
//    private List<Operation> operation;
//    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
//    private List<OrderEntity> OrderEntity;
//    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
//    private List<Style> style;
    

}
