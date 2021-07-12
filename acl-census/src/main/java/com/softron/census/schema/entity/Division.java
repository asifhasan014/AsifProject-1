package com.softron.census.schema.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DIVISION")
public class Division implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8645976870237473738L;

    /**
     * Division id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    /**
     * Division name.
     */
    @Column(name = "NAME")
    private String name;

    @Column(name = "REMARK")
    private String remarks;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = false;

    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Long displayOrder;

    @JsonIgnore
    @OneToMany(mappedBy = "division")
    private List<District> district;

    @JsonIgnore
    @OneToMany(mappedBy = "division")
    private List<CensusOrganization> censusOrganization;

    /**
     *
     * Constructor.
     *
     */
    public Division() {
        super();
    }

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

    public String getRemarks() {
        return remarks;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public List<CensusOrganization> getCensusOrganization() {
        return censusOrganization;
    }

    public void setCensusOrganization(List<CensusOrganization> censusOrganization) {
        this.censusOrganization = censusOrganization;
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

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

}
