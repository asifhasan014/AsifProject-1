/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softron.census.schema.constant.OfficeTypeEnum;
import com.softron.census.schema.constant.OrgTypeEnum;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "CENSUS_ORGANIZATION")
public class CensusOrganization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME_ENGLISH", nullable = false)
    private String nameEng;

    @Column(name = "NAME_BANGLA", nullable = false)
    private String nameBang;

    @Column(name = "WEB_ADDRESS", nullable = true)
    private String webAddress;

    @Column(name = "TELEPHONE", nullable = true)
    private String telephone;

    @Column(name = "ADDRESS", nullable = true)
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REMARKS", nullable = true)
    private String remarks;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "PARENT_ORG_ID")
    private Long parentId;

    @Column(name = "FAX", nullable = true)
    private String fax;

    @Column(name = "CENSUS_ORDER")
    private Long order;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ORG_TYPE")
    private OrgTypeEnum orgType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "OFFICE_TYPE")
    private OfficeTypeEnum officeType;

    @JoinColumn(name = "DIVISION_ID", referencedColumnName = "id")
    @ManyToOne
    private Division division;

    @JoinColumn(name = "DISTRICT_ID", referencedColumnName = "id")
    @ManyToOne
    private District district;

    @JsonIgnore
    @OneToMany(mappedBy = "censusOrganization")
    private List<Project> project;
    @JsonIgnore
    @OneToMany(mappedBy = "censusOrganization")
    private List<CensusOfficeHierarchy> censusOfficeHierarchys;
    @JsonIgnore
    @OneToMany(mappedBy = "censusOrganization")
    private List<CensusDataEntry> censusDataEntrys;
    @JsonIgnore
    @OneToMany(mappedBy = "censusOrganization")
    private List<CensusDataEntryProject> censusDataEntryProjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public List<CensusOfficeHierarchy> getCensusOfficeHierarchys() {
        return censusOfficeHierarchys;
    }

    public void setCensusOfficeHierarchys(List<CensusOfficeHierarchy> censusOfficeHierarchys) {
        this.censusOfficeHierarchys = censusOfficeHierarchys;
    }

    public List<CensusDataEntry> getCensusDataEntrys() {
        return censusDataEntrys;
    }

    public void setCensusDataEntrys(List<CensusDataEntry> censusDataEntrys) {
        this.censusDataEntrys = censusDataEntrys;
    }

    public List<CensusDataEntryProject> getCensusDataEntryProjects() {
        return censusDataEntryProjects;
    }

    public void setCensusDataEntryProjects(List<CensusDataEntryProject> censusDataEntryProjects) {
        this.censusDataEntryProjects = censusDataEntryProjects;
    }

    public String getNameBang() {
        return nameBang;
    }

    public void setNameBang(String nameBang) {
        this.nameBang = nameBang;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public OrgTypeEnum getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgTypeEnum orgType) {
        this.orgType = orgType;
    }

    public OfficeTypeEnum getOfficeType() {
        return officeType;
    }

    public void setOfficeType(OfficeTypeEnum officeType) {
        this.officeType = officeType;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

}
