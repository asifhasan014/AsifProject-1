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
public class ReportModelTO {

    private String html;
    private boolean isChart;
    private BarChart barChart;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isIsChart() {
        return isChart;
    }

    public void setIsChart(boolean isChart) {
        this.isChart = isChart;
    }

    public BarChart getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

}
