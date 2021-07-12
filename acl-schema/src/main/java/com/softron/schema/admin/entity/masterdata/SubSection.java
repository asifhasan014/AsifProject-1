package com.softron.schema.admin.entity.masterdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SubSection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	@ManyToOne
	@JoinColumn(name="SECTION_ID",referencedColumnName = "id")
	private Section section;
	
	private String subSectionHead;
	
	private long capacity;
	
	private long numberOfMachine;
	
	private long numberOfLabor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getSubSectionHead() {
		return subSectionHead;
	}

	public void setSubSectionHead(String subSectionHead) {
		this.subSectionHead = subSectionHead;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public long getNumberOfMachine() {
		return numberOfMachine;
	}

	public void setNumberOfMachine(long numberOfMachine) {
		this.numberOfMachine = numberOfMachine;
	}

	public long getNumberOfLabor() {
		return numberOfLabor;
	}

	public void setNumberOfLabor(long numberOfLabor) {
		this.numberOfLabor = numberOfLabor;
	}
	
	
	
	
	

}
