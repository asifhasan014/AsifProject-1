package com.softron.schema.admin.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.softron.schema.admin.entity.masterdata.BaseModel;
import com.softron.schema.admin.entity.masterdata.Employee;



import lombok.Data;

@Entity
@Data
public class FileUpload extends BaseModel{

	private String filePath;
	
	private String thumbnailPath;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
