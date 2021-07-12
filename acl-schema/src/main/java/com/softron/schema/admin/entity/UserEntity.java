package com.softron.schema.admin.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.softron.datastore.Auditable;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.production.ReportLayout;
import com.softron.schema.capacitystudy.entity.CapacityStudy;

/**
 * 
 * Entity to persist user information in data store.
 *
 * @author Mozahid
 * @version 1.0
 */
@Entity
@Table(name = "USERS")
public class UserEntity extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 2877879787472382731L;

	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userName;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "GOVT_ID")
	private String govtId;

	@Column(name = "MOBILE", nullable = false)
	private String mobile;

	@Column(name = "ENABLED")
	private boolean enabled = true;

	@Column(name = "ACCOUNT_NON_EXPIRED")
	private boolean accountNonExpired = true;

	@Column(name = "ACCOUNT_NON_LOCKED")
	private boolean accountNonLocked = true;

	@Column(name = "CREDENTIALS_NON_EXPIRED")
	private boolean credentialsNonExpired = true;

	@Column(name = "LAST_UPDATED_ON")
	private LocalDate lastUpdatedOn;

	/**
	 * User roles.
	 */
//    @OneToOne(mappedBy = "sharedUser")
//    private CapacityStudy capacityStudy;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Role> roles;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Module> assignedModules;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Client> client;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<ReportLayout> ReportLayoutList;

	@ManyToOne
	@JoinColumn(name = "ORG_ID", referencedColumnName = "id")
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public LocalDate getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(LocalDate lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Module> getAssignedModules() {
		return assignedModules;
	}

	public void setAssignedModules(Collection<Module> assignedModules) {
		this.assignedModules = assignedModules;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<Client> getClient() {
		return client;
	}

	public void setClient(List<Client> client) {
		this.client = client;
	}

}
