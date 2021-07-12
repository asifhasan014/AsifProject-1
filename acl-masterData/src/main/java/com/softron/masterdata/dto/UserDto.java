package com.softron.masterdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
public class UserDto {

    private String userName;

    private String fullName;

    private String email;

    private String password;

    private String govtId;

    private String mobile;

    private boolean enabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private LocalDate lastUpdatedOn;
    
    @JsonIgnoreProperties({ "section","workProcess","employee", "operation","defect","qualityTransaction","client","orderEntity","style","userEntity","operationMachine","targetAndManpower","capacityStudy","customer","qualityDefect","settings","device","machine","item"})
	private Organization organization;

//    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Collection<Role> roles;
//
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Collection<Module> assignedModules;
//    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Client> client;*/
////
////    @JsonIgnoreProperties("user")
////    private List<ClientDto> client;

}
