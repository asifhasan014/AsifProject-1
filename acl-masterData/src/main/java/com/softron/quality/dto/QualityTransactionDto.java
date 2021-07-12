package com.softron.quality.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.dto.SectionDto;
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.dto.WorkProcessDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Employee;

import lombok.Data;

@Data
public class QualityTransactionDto {
	
	
	private Long id;
    
	//@JsonIgnoreProperties({"plant","parent","subSection","qualityTransaction"})
	//@JsonIgnoreProperties({"plant", "parent","subSection"})
//    private SectionDto section; 
	
	//@JsonIgnoreProperties({"customer","qualityTransaction","operationBreakDown","orderEntity"})
	//@JsonIgnoreProperties({"operationBreakDown"})
   // private StyleDto style; 
    
	//@JsonIgnoreProperties({"qualityTransaction","workProcess"})
	//@JsonIgnoreProperties({"workProcess"})
    private QualityTypeDto qualityType; 
    
	//@JsonIgnoreProperties({"operation","employee","defect","qualityTransaction","qualityType"})
    private WorkProcessDto workProcess; 
	
	//@JsonIgnoreProperties({"workProcess","operationBreakDown","qualityTransaction","qualityDefect"})
   // @JsonIgnoreProperties({"workProcess"})
    private OperationDto operation;
    
    @JsonIgnoreProperties({"workProcess", "department"})
    private EmployeeDto employee;
    
    @JsonIgnoreProperties({"style", "customer"})
    private OrderEntityDto orderEntity;
    
    @JsonIgnoreProperties("orderEntity")
    private VarienceDto varience;
    
    private long sampleSize;
    
    private String checkOutput;
    
    private int defectiveItem;
    
   // private Date date;
    
    private Time elapsedTime;
    
    private Date dateTime;
    
    @JsonIgnoreProperties({"defect","operation","qualityTransaction"})
    //@JsonIgnoreProperties({"qualityTransaction"})
	private List<QualityDefectDto> qualityDefect;
    
    private List<QualityDefectDto2> newQualityDefect;

    private Long totalCheck;
    
    private Long totalQcPass;
    
    private Long totalAlter;
    
    private Long totalReject;
    
    @JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower" })
	private Organization organization;
    
    private String newCreateAtString;
    
    //private int totalDefect;
    
    private String createdUser;
	
}
