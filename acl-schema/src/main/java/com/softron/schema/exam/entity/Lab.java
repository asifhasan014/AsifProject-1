package com.softron.schema.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softron.schema.admin.entity.ExamOrganization;
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

@Entity
@Table(name = "LAB")
public class Lab implements Serializable {

    private static final long serialVersionUID = -8696916386274990433L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @JoinColumn(name = "ORGANIZATION_ID", nullable = true, referencedColumnName = "id")
    @ManyToOne
    private ExamOrganization organization;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lab", fetch = FetchType.LAZY)
    private List<LabPC> labPCs = new ArrayList<>();

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

    public List<LabPC> getLabPCs() {
        return labPCs;
    }

    public void setLabPCs(List<LabPC> labPCs) {
        this.labPCs = labPCs;
    }
}
