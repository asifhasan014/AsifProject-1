/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.domain;

import java.util.List;

/**
 *
 * @author Mozahid
 */
public class BarChartData {

    private String backgroundColor;

    private String borderColor;

    private long borderWidth;

    private List<String> data;

    private String label;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public long getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(long borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ClassPojo [backgroundColor=" + backgroundColor + ", borderColor=" + borderColor + ", borderWidth=" + borderWidth + ", data=" + data + ", label=" + label + ']';
    }

}
