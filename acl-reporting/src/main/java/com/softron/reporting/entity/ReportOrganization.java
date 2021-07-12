package com.softron.reporting.entity;

import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OR_ORGANIZATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReportOrganization extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 3857732116399697935L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME_ENGLISH", nullable = false)
    private String nameEng;

    @Column(name = "NAME_BANGLA", nullable = false)
    private String nameBang;

    @Column(name = "WEB_ADDRESS")
    private String webAddress;

    @Column(name = "TELEPHONE", nullable = false)
    private String telephone;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "ORG_TYPE")
    private Long orgType;

    @Column(name = "PARENT_ORG")
    private Long parentId;

    @Version
    private int version;

}
