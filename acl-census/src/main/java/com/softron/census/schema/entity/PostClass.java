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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "POST_CLASS")
public class PostClass implements Serializable {

    private static final long serialVersionUID = -5447904228457241750L;
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
    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Long displayOrder;
    @JsonIgnore
    @OneToMany(mappedBy = "postClass")
    private List<Post> post;
    @JsonIgnore
    @OneToMany(mappedBy = "postClass")
    private List<PayScale> payScale;
    @JsonIgnore
    @OneToMany(mappedBy = "postClass")
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

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<PayScale> getPayScale() {
        return payScale;
    }

    public void setPayScale(List<PayScale> payScale) {
        this.payScale = payScale;
    }

    public List<CensusDataEntryProject> getCensusDataEntryProjects() {
        return censusDataEntryProjects;
    }

    public void setCensusDataEntryProjects(List<CensusDataEntryProject> censusDataEntryProjects) {
        this.censusDataEntryProjects = censusDataEntryProjects;
    }

  
}
