package com.softron.admin.transferobjects;

import java.util.List;

public class ModuleTO {

    private Long id;

    private String name;

    private String remarks;

    private boolean active;

    private boolean macBindingEnabled;

    private boolean ipBindingEnabled;

    private List<String> macAccessList;

    private List<String> ipAccessList;

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

    public void setRemarks(String remark) {
        this.remarks = remark;
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

    public List<String> getMacAccessList() {
        return macAccessList;
    }

    public void setMacAccessList(List<String> macAccessList) {
        this.macAccessList = macAccessList;
    }

    public List<String> getIpAccessList() {
        return ipAccessList;
    }

    public void setIpAccessList(List<String> ipAccessList) {
        this.ipAccessList = ipAccessList;
    }

}
