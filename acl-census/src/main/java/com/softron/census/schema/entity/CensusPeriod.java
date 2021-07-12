package com.softron.census.schema.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CENSUS_PERIOD")
public class CensusPeriod implements Serializable {

    private static final long serialVersionUID = -5447904228457241750L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CENSUS_PERIOD", nullable = false)
    private String censusPeriod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @JsonIgnore
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "END_DATE")
    @JsonIgnore
    private LocalDate endDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "LAST_DATE_DATA_ENTRY")
    @JsonIgnore
    private LocalDate lastDateDataEntry;

    @Column(name = "ACTIVE")
    private boolean active;

    @JoinColumn(name = "PAY_SCALE_YEAR_ID", referencedColumnName = "id")
    @ManyToOne
    private PayScaleYear payScaleYear;
    @JsonIgnore
    @OneToMany(mappedBy = "censusPeriod")
    private List<CensusDataEntryProject> censusDataEntryProjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCensusPeriod() {
        return censusPeriod;
    }

    public void setCensusPeriod(String censusPeriod) {
        this.censusPeriod = censusPeriod;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<CensusDataEntryProject> getCensusDataEntryProjects() {
        return censusDataEntryProjects;
    }

    public void setCensusDataEntryProjects(List<CensusDataEntryProject> censusDataEntryProjects) {
        this.censusDataEntryProjects = censusDataEntryProjects;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getLastDateDataEntry() {
        return lastDateDataEntry;
    }

    public void setLastDateDataEntry(LocalDate lastDateDataEntry) {
        this.lastDateDataEntry = lastDateDataEntry;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PayScaleYear getPayScaleYear() {
        return payScaleYear;
    }

    public void setPayScaleYear(PayScaleYear payScaleYear) {
        this.payScaleYear = payScaleYear;
    }

}
