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
@Table(name = "PAY_SCALE_YEAR")
public class PayScaleYear implements Serializable {

    private static final long serialVersionUID = -5447904228457241750L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "CENSUS_ORDER")
    private Long displayOrder;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @Column(name = "REMARKS", nullable = true)
    private String remarks;
    @JsonIgnore
    @OneToMany(mappedBy = "payScaleYear")
    private List<PayScale> payScale;
    @JsonIgnore
    @OneToMany(mappedBy = "payScaleYear")
    private List<CensusPeriod> censusPeriods;

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<CensusPeriod> getCensusPeriods() {
        return censusPeriods;
    }

    public void setCensusPeriods(List<CensusPeriod> censusPeriods) {
        this.censusPeriods = censusPeriods;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PayScale> getPayScale() {
        return payScale;
    }

    public void setPayScale(List<PayScale> payScale) {
        this.payScale = payScale;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
