package com.softron.schema.exam.entity;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softron.schema.admin.entity.ExamOrganization;

@Entity
@Table(name = "EXAM")
public class Exam implements Serializable {

    private static final long serialVersionUID = -8696916386274990433L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @JoinColumn(name = "ORGANIZATION_ID", nullable = true, referencedColumnName = "id")
    @ManyToOne
    private ExamOrganization organization;
    @JoinColumn(name = "DESIGNATION_ID", nullable = true, referencedColumnName = "id")
    @ManyToOne
    private Designation designation;
    @Column(name = "EXAM_ORDER_ENGLISH", nullable = true)
    private Boolean examOrderEnglish;
    @Column(name = "LANGUAGE", nullable = true)
    private String language;
    @Column(name = "EXAM_YEAR", nullable = true)
    private Long examYear;
    @Column(name = "EDITABLE", nullable = true)
    private Boolean editable = true;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam", fetch = FetchType.LAZY)
    private List<ExamSession> examSessions = new ArrayList<>();

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

    public ExamOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(ExamOrganization organization) {
        this.organization = organization;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Boolean getExamOrderEnglish() {
        return examOrderEnglish;
    }

    public void setExamOrderEnglish(Boolean examOrderEnglish) {
        this.examOrderEnglish = examOrderEnglish;
    }

    public List<ExamSession> getExamSessions() {
        return examSessions;
    }

    public void setExamSessions(List<ExamSession> examSessions) {
        this.examSessions = examSessions;
    }

    public Long getExamYear() {
        return examYear;
    }

    public void setExamYear(Long examYear) {
        this.examYear = examYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

}
