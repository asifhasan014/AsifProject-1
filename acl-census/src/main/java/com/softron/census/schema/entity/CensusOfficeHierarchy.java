/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.softron.census.schema.constant.OrgTypeEnum;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "CENSUS_OFFICE_HIERARCHY")
public class CensusOfficeHierarchy implements Serializable {

    private static final long serialVersionUID = -5447904228457241750L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "OFFICE_NAME_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusOffice censusOffice;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @JoinColumn(name = "CENSUS_ORGANIZATION_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusOrganization censusOrganization;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ORG_TYPE")
    private OrgTypeEnum orgType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CensusOffice getCensusOffice() {
        return censusOffice;
    }

    public void setCensusOffice(CensusOffice censusOffice) {
        this.censusOffice = censusOffice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CensusOrganization getCensusOrganization() {
        return censusOrganization;
    }

    public void setCensusOrganization(CensusOrganization censusOrganization) {
        this.censusOrganization = censusOrganization;
    }

    public OrgTypeEnum getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgTypeEnum orgType) {
        this.orgType = orgType;
    }

}
