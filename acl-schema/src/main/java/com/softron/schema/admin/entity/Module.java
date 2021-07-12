package com.softron.schema.admin.entity;

import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ACL_MODULES")
public class Module extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 2611020388454700269L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "MAC_BINDING_ENABLED")
    private boolean macBindingEnabled;

    @Column(name = "IP_BINDING_ENABLED")
    private boolean ipBindingEnabled;

    @Column(name = "MAC_ACCESS_LIST")
    private String macAccessList;

    @Column(name = "IP_ACCESS_LIST")
    private String ipAccessList;

    @Column(name = "MODULE_CONTEXT_PATH")
    private String contextPath;

    @Column(name = "MODULE_IMAGE")
    private String image;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isMacBindingEnabled() {
        return macBindingEnabled;
    }

    public void setMacBindingEnabled(boolean macBindingEnabled) {
        this.macBindingEnabled = macBindingEnabled;
    }

    public boolean isIpBindingEnabled() {
        return ipBindingEnabled;
    }

    public void setIpBindingEnabled(boolean ipBindingEnabled) {
        this.ipBindingEnabled = ipBindingEnabled;
    }

    public String getMacAccessList() {
        return macAccessList;
    }

    public void setMacAccessList(String macAccessList) {
        this.macAccessList = macAccessList;
    }

    public String getIpAccessList() {
        return ipAccessList;
    }

    public void setIpAccessList(String ipAccessList) {
        this.ipAccessList = ipAccessList;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
