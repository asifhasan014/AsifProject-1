/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mozahid
 */
public class BarChart {

    private List<BarChartData> datasets = new ArrayList<>();

    private List<String> labels = new ArrayList<>();

    public List<BarChartData> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<BarChartData> datasets) {
        this.datasets = datasets;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
