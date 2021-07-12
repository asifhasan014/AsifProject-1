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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "CODE", nullable = true)
    private String code;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @JoinColumn(name = "CENSUS_ORGANIZATION_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusOrganization censusOrganization;
    @JoinColumn(name = "PROJECT_TYPE_ID", referencedColumnName = "id")
    @ManyToOne
    private ProjectType projectType;
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<CensusDataEntryProject> censusDataEntryProjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<CensusDataEntryProject> getCensusDataEntryProjects() {
        return censusDataEntryProjects;
    }

    public void setCensusDataEntryProjects(List<CensusDataEntryProject> censusDataEntryProjects) {
        this.censusDataEntryProjects = censusDataEntryProjects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
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

    public CensusOrganization getCensusOrganization() {
        return censusOrganization;
    }

    public void setCensusOrganization(CensusOrganization censusOrganization) {
        this.censusOrganization = censusOrganization;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

}
