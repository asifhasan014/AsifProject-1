package com.softron.admin.dto;

import java.util.List;

//import com.softron.masterdata.dto.ClientDto;
//import com.softron.masterdata.dto.SectionDto;
import com.softron.schema.admin.entity.Settings;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;

@Data
public class OrganizationDto{

    private Long id;

    private String name;
    
    private String head;
    
    private String code;
    
    private String type;
    
    private String nameEng;

    private String nameBang;

    private String webAddress;

    private String telephone;

    private String address;

    private String email;

    private String remarks;

    private boolean active;

    private Long orgType;

    private Long locationId;

    private Long parentId;
    
//    private List<SectionDto> section;
    
    private SettingsDto settings;
    
    private List<Employee> employee;
    
    private List<WorkProcess> workProcess;
    
    private List<Operation> operation;
    
    private List<Defect> defect;
    
//    private List<ClientDto> client;
    
    private List<OrderEntity> orderEntity;
    
    private List<Style> style;
    
    private List<UserEntity> userEntity;
    
    private List<OperationMachine> operationMachine;
    
    private List<TargetAndManpower> targetAndManpower;
    
    private List<QualityTransaction> qualityTransaction;
  
}
