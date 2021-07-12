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

@Entity
@Table(name = "DISTRICT")
public class District implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8645976870237473738L;

    /**
     * DISTRICT id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    /**
     * DISTRICT name.
     */
    @Column(name = "NAME")
    private String name;

    @Column(name = "REMARK")
    private String remarks;

    @JoinColumn(name = "DIVISION_ID", referencedColumnName = "id")
    @ManyToOne
    private Division division;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = false;

    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Long displayOrder;

    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private List<CensusOrganization> censusOrganizations;

    /**
     *
     * Constructor.
     *
     */
    public District() {
        super();
    }

    public Long getId() {
        return id;
    }

    public List<CensusOrganization> getCensusOrganizations() {
        return censusOrganizations;
    }

    public void setCensusOrganizations(List<CensusOrganization> censusOrganizations) {
        this.censusOrganizations = censusOrganizations;
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

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
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
