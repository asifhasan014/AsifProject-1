package com.softron.masterdata.dto;

import java.time.LocalDate;

import com.softron.schema.admin.entity.Organization;

import lombok.Data;

@Data
public class CoWorkerCSDto {

	private String userId;
	
	private String userName;

    private String fullName;

    private String email;

    private String mobile;
    
    private Organization organization;
}
