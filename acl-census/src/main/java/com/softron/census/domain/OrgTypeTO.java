/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.domain;

/**
 *
 * @author Mozahid
 */
public class OrgTypeTO {

    private String name;
    private String value;

    public OrgTypeTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public OrgTypeTO() {
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
