package com.softron.security.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.softron.schema.admin.entity.Module;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.Role;
import com.softron.schema.admin.entity.UserEntity;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserTO {

    @NotBlank(message = "User name can't be blank.")
    private String userName;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Full name can't be blank.")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    private String govtId;

    @Size(min = 10, max = 20, message = "Mobile number must be between 10 and 20 characters")
    private String mobile;

    private String roles;

    private String modules;

    private List<Long> rolesIds;

    private List<Long> moduleIds;
    
    private boolean editing;
    
    private Long orgId;

    public UserTO() {
        super();
    }

    public static UserTO fromUserEntity(final UserEntity user) {
        final UserTO userTO = new UserTO();
        userTO.setUserName(user.getUserName());
        userTO.setPassword(user.getPassword());
        userTO.setFullName(user.getFullName());
        userTO.setEmail(user.getEmail());
        userTO.setGovtId(user.getGovtId());
        userTO.setMobile(user.getMobile());
        userTO.setRolesIds(user.getRoles().stream().map(Role::getId).collect(toList()));
        userTO.setRoles(user.getRoles().stream().map(Role::getName).collect(joining(", ")));
        userTO.setModuleIds(user.getAssignedModules().stream().map(Module::getId).collect(toList()));
        userTO.setModules(user.getAssignedModules().stream().map(Module::getName).collect(joining(", ")));

//        userTO.setOrgId(user.getOrganization());
        userTO.setOrgId(user.getOrganization().getId());
        return userTO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String firstName) {
        this.fullName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGovtId() {
        return govtId;
    }

    public void setGovtId(String govtId) {
        this.govtId = govtId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public List<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }

    public List<Long> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Long> moduleIds) {
        this.moduleIds = moduleIds;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	
	
    

}
