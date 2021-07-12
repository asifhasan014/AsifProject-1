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
@Table(name = "PAY_SCALE")
public class PayScale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "CODE", nullable = true)
    private String code;
    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Long displayOrder;
    @JoinColumn(name = "POST_CLASS_ID", referencedColumnName = "id")
    @ManyToOne
    private PostClass postClass;
    @JoinColumn(name = "GRADE_ID", referencedColumnName = "id")
    @ManyToOne
    private Grade grade;
    @JoinColumn(name = "PAY_SCALE_YEAR_ID", referencedColumnName = "id")
    @ManyToOne
    private PayScaleYear payScaleYear;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "payScale")
    private List<CensusDataEntryProject> censusDataEntryProjects;
    @JsonIgnore
    @OneToMany(mappedBy = "payScale")
    private List<CensusDataEntry> censusDataEntrys;

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

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public PostClass getPostClass() {
        return postClass;
    }

    public void setPostClass(PostClass postClass) {
        this.postClass = postClass;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public PayScaleYear getPayScaleYear() {
        return payScaleYear;
    }

    public void setPayScaleYear(PayScaleYear payScaleYear) {
        this.payScaleYear = payScaleYear;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<CensusDataEntryProject> getCensusDataEntryProjects() {
        return censusDataEntryProjects;
    }

    public void setCensusDataEntryProjects(List<CensusDataEntryProject> censusDataEntryProjects) {
        this.censusDataEntryProjects = censusDataEntryProjects;
    }

    public List<CensusDataEntry> getCensusDataEntrys() {
        return censusDataEntrys;
    }

    public void setCensusDataEntrys(List<CensusDataEntry> censusDataEntrys) {
        this.censusDataEntrys = censusDataEntrys;
    }

}
