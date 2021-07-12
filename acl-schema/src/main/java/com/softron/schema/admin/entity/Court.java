package com.softron.schema.admin.entity;

import com.softron.core.enums.CaseFormType;
import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LEGAL_COURTS")
public class Court extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = -8696916386274990433L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    
    @Column(name = "CASE_FORM_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CaseFormType caseFormType;

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

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CaseFormType getCaseFormType() {
        return caseFormType;
    }

    public void setCaseFormType(CaseFormType caseFormType) {
        this.caseFormType = caseFormType;
    }
    
    

}
