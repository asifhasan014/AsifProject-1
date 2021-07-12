/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "EXAM_SESSION")
public class ExamSession implements Serializable {

    private static final long serialVersionUID = -8696916386274990433L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "SESSION_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionDate;
    @Column(name = "SESSION_START_TIME", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionStartTime;
    @JoinColumn(name = "LAB_ID", nullable = true)
    @ManyToOne
    private Lab lab;
    @Column(name = "BANGLA_TEXT", nullable = false)
    private String banglaText;
    @Column(name = "ENGLISH_TEXT", nullable = false)
    private String englishText;
    @Column(name = "NAME")
    private String name;
    @JsonIgnore
    @JoinColumn(name = "EXAM_ID", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Exam exam;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examSession", fetch = FetchType.LAZY)
    private List<Candidate> candidates = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Date getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(Date sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public String getBanglaText() {
        return banglaText;
    }

    public void setBanglaText(String banglaText) {
        this.banglaText = banglaText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
