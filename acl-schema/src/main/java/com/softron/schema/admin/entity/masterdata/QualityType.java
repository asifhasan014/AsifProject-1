package com.softron.schema.admin.entity.masterdata;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.softron.schema.admin.entity.Menu;
import lombok.Data;

@Entity
@Data
public class QualityType extends BaseModel{
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "workProcessId", referencedColumnName = "id")

	private WorkProcess workProcess;
	private Integer isOperationWise;
	private Integer sampleSize;
	private Integer type;

	/*@OneToOne(mappedBy = "detail")
	private Menu menu;*/
}
