package com.softron.schema.admin.entity;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.softron.schema.exam.constant.OfficeTypeEnum;
import com.softron.schema.exam.constant.OrgTypeEnum;
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
/**
 *
 * @author Mozahid
 */
@Entity
@Table(name = "EXAM_ORGANIZATION")
public class ExamOrganization implements Serializable {

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

    @Column(name = "PARENT_ORG_ID")
    private Long parentId;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "DISPLAY_ORDER")
    private Long order;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ORG_TYPE")
    private OrgTypeEnum orgType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "OFFICE_TYPE")
    private OfficeTypeEnum officeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameBang() {
        return nameBang;
    }

    public void setNameBang(String nameBang) {
        this.nameBang = nameBang;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
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

