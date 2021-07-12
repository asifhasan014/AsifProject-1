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

import com.softron.census.schema.constant.OfficeTypeEnum;
import com.softron.census.schema.constant.OrgTypeEnum;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "CENSUS_DATA_ENTRY")
public class CensusDataEntry implements Serializable {

    private static final long serialVersionUID = -5447904228457241750L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "REMARKS", nullable = true)
    private String remarks;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @Column(name = "TOTAL_SANC_POST", nullable = false)
    private Long totalSancPost;
    @Column(name = "CADRE_SANC_POST", nullable = false)
    private Long cadreSancPost;
    @Column(name = "CADRE_MALE", nullable = false)
    private Long cadreMale;
    @Column(name = "CADRE_FEMALE", nullable = false)
    private Long cadreFemale;
    @Column(name = "NON_CADRE_MALE", nullable = false)
    private Long nonCadreMale;
    @Column(name = "NON_CADRE_FEMALE", nullable = false)
    private Long nonCadreFemale;
    @Column(name = "TOTAL_EXISTING_MANPOWER", nullable = false)
    private Long totalExistingManpower;
    @Column(name = "VACANT", nullable = false)
    private Long vacant;
    @Column(name = "SURPLUS", nullable = false)
    private Long surplus;
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusOrganization censusOrganization;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ORG_TYPE")
    private OrgTypeEnum orgType;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "OFFICE_TYPE")
    private OfficeTypeEnum officeType;
    @JoinColumn(name = "CENSUS_PERIOD_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusPeriod censusPeriod;
    @JoinColumn(name = "POST_CLASS_ID", referencedColumnName = "id")
    @ManyToOne
    private PostClass postClass;
    @JoinColumn(name = "GRADE_ID", referencedColumnName = "id")
    @ManyToOne
    private Grade grade;
    @JoinColumn(name = "PAY_SCALE_ID", referencedColumnName = "id")
    @ManyToOne
    private PayScale payScale;
    @JoinColumn(name = "POST_ID", referencedColumnName = "id")
    @ManyToOne
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CensusPeriod getCensusPeriod() {
        return censusPeriod;
    }

    public void setCensusPeriod(CensusPeriod censusPeriod) {
        this.censusPeriod = censusPeriod;
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

    public PayScale getPayScale() {
        return payScale;
    }

    public void setPayScale(PayScale payScale) {
        this.payScale = payScale;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Long getTotalSancPost() {
        return totalSancPost;
    }

    public void setTotalSancPost(Long totalSancPost) {
        this.totalSancPost = totalSancPost;
    }

    public Long getCadreSancPost() {
        return cadreSancPost;
    }

    public void setCadreSancPost(Long cadreSancPost) {
        this.cadreSancPost = cadreSancPost;
    }

    public Long getCadreMale() {
        return cadreMale;
    }

    public void setCadreMale(Long cadreMale) {
        this.cadreMale = cadreMale;
    }

    public Long getCadreFemale() {
        return cadreFemale;
    }

    public void setCadreFemale(Long cadreFemale) {
        this.cadreFemale = cadreFemale;
    }

    public Long getNonCadreMale() {
        return nonCadreMale;
    }

    public void setNonCadreMale(Long nonCadreMale) {
        this.nonCadreMale = nonCadreMale;
    }

    public Long getNonCadreFemale() {
        return nonCadreFemale;
    }

    public void setNonCadreFemale(Long nonCadreFemale) {
        this.nonCadreFemale = nonCadreFemale;
    }

    public Long getTotalExistingManpower() {
        return totalExistingManpower;
    }

    public void setTotalExistingManpower(Long totalExistingManpower) {
        this.totalExistingManpower = totalExistingManpower;
    }

    public Long getVacant() {
        return vacant;
    }

    public void setVacant(Long vacant) {
        this.vacant = vacant;
    }

    public Long getSurplus() {
        return surplus;
    }

    public void setSurplus(Long surplus) {
        this.surplus = surplus;
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

    public OfficeTypeEnum getOfficeType() {
        return officeType;
    }

    public void setOfficeType(OfficeTypeEnum officeType) {
        this.officeType = officeType;
    }

}
