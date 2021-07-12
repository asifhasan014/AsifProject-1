package com.softron.schema.admin.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.datastore.Auditable;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Device;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Item;
import com.softron.schema.admin.entity.masterdata.Machine;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.capacitystudy.entity.CapacityStudy;
import com.softron.schema.qualitymodule.entity.QualityDefect;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","settings","qualityDefect","reportLayout","capacityStudy","device","machine","item" })
@Entity
@Table(name = "ORGANIZATIONS")
@Data
public class Organization extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 3857732116399697935L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "HEAD")
    private String head;
    
    @Column(name = "CODE")
    private String code;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "NAME_ENGLISH")
    private String nameEng;

    @Column(name = "NAME_BANGLA")
    private String nameBang;

    @Column(name = "WEB_ADDRESS")
    private String webAddress;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "ORG_TYPE")
    private Long orgType;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column(name = "PARENT_ORG")
    private Long parentId;
    
    @Column(name = "ROOT_PATH")
    private String rootPath;
    
    @Column(name = "IS_LINE")
    private Boolean isLine;
    
    @PrePersist
	public void prePersist() {
		this.isLine = false;
	}
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy ="organization")
	private List<Section> section;
    
	/*
	 * @OneToMany(cascade = CascadeType.ALL,mappedBy ="plant") private List<Section>
	 * section;
	 */

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<Employee> employee;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<WorkProcess> workProcess;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<Operation> operation;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<Defect> defect;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<Client> client;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<OrderEntity> orderEntity;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
	private List<Style> style;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organization")
   	private List<UserEntity> userEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id")
    private Settings settings;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
	private List<OperationMachine> operationMachine;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
	private List<TargetAndManpower> targetAndManpower;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
	private List<QualityTransaction> qualityTransaction;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
   	private List<QualityDefect> qualityDefect;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
   	private List<CapacityStudy> capacityStudy;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
   	private List<Device> device;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
   	private List<Machine> machine;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
   	private List<Item> item;
}
