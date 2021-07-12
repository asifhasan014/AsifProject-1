/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "CENSUS_DATA_ENTRY_PROJECT")
public class CensusDataEntryProject implements Serializable {

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
    @Column(name = "REVENUE_PERSONNEL_MALE", nullable = false)
    private Long revenuePersonnelMale;
    @Column(name = "REVENUE_PERSONNEL_FEMALE", nullable = false)
    private Long revenuePersonnelFemale;
    @Column(name = "PROJECT_PERSONNEL_FEMALE", nullable = false)
    private Long projectPersonnelFemale;
    @Column(name = "PROJECT_PERSONNEL_MALE", nullable = false)
    private Long projectPersonnelMale;
    @Column(name = "TOTAL_EXISTING_MANPOWER", nullable = false)
    private Long totalExistingManpower;
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusOrganization censusOrganization;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "id")
    @ManyToOne
    private Project project;
    @JoinColumn(name = "CENSUS_PERIOD_ID", referencedColumnName = "id")
    @ManyToOne
    private CensusPeriod censusPeriod;
    @JoinColumn(name = "POST_ID", referencedColumnName = "id")
    @ManyToOne
    private Post post;
    @JoinColumn(name = "POST_CLASS_ID", referencedColumnName = "id")
    @ManyToOne
    private PostClass postClass;
    @JoinColumn(name = "GRADE_ID", referencedColumnName = "id")
    @ManyToOne
    private Grade grade;
    @JoinColumn(name = "PAY_SCALE_ID", referencedColumnName = "id")
    @ManyToOne
    private PayScale payScale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CensusPeriod getCensusPeriod() {
        return censusPeriod;
    }

    public void setCensusPeriod(CensusPeriod censusPeriod) {
        this.censusPeriod = censusPeriod;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Long getRevenuePersonnelMale() {
        return revenuePersonnelMale;
    }

    public void setRevenuePersonnelMale(Long revenuePersonnelMale) {
        this.revenuePersonnelMale = revenuePersonnelMale;
    }

    public Long getRevenuePersonnelFemale() {
        return revenuePersonnelFemale;
    }

    public void setRevenuePersonnelFemale(Long revenuePersonnelFemale) {
        this.revenuePersonnelFemale = revenuePersonnelFemale;
    }

    public Long getProjectPersonnelFemale() {
        return projectPersonnelFemale;
    }

    public void setProjectPersonnelFemale(Long projectPersonnelFemale) {
        this.projectPersonnelFemale = projectPersonnelFemale;
    }

    public Long getProjectPersonnelMale() {
        return projectPersonnelMale;
    }

    public void setProjectPersonnelMale(Long projectPersonnelMale) {
        this.projectPersonnelMale = projectPersonnelMale;
    }

    public Long getTotalExistingManpower() {
        return totalExistingManpower;
    }

    public void setTotalExistingManpower(Long totalExistingManpower) {
        this.totalExistingManpower = totalExistingManpower;
    }

    public CensusOrganization getCensusOrganization() {
        return censusOrganization;
    }

    public void setCensusOrganization(CensusOrganization censusOrganization) {
        this.censusOrganization = censusOrganization;
    }

}
