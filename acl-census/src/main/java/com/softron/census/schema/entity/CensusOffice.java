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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softron.census.schema.constant.OrgTypeEnum;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "CENSUS_OFFICE")
public class CensusOffice implements Serializable {

    private static final long serialVersionUID = -5447904228457241750L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ORG_TYPE")
    private OrgTypeEnum orgType;
    @JsonIgnore
    @OneToMany(mappedBy = "censusOffice")
    private List<CensusOfficeHierarchy> censusOfficeHierarchys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public List<CensusOfficeHierarchy> getCensusOfficeHierarchys() {
        return censusOfficeHierarchys;
    }

    public void setCensusOfficeHierarchys(List<CensusOfficeHierarchy> censusOfficeHierarchys) {
        this.censusOfficeHierarchys = censusOfficeHierarchys;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public OrgTypeEnum getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgTypeEnum orgType) {
        this.orgType = orgType;
    }

}
