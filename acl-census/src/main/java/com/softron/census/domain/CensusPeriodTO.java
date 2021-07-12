package com.softron.census.domain;

import com.softron.census.schema.entity.PayScaleYear;

public class CensusPeriodTO {

    private Long id;
    private String censusPeriod;
    private String startDate;
    private String endDate;
    private String lastDateDataEntry;
    private boolean active;
    private PayScaleYear payScaleYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCensusPeriod() {
        return censusPeriod;
    }

    public void setCensusPeriod(String censusPeriod) {
        this.censusPeriod = censusPeriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLastDateDataEntry() {
        return lastDateDataEntry;
    }

    public void setLastDateDataEntry(String lastDateDataEntry) {
        this.lastDateDataEntry = lastDateDataEntry;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PayScaleYear getPayScaleYear() {
        return payScaleYear;
    }

    public void setPayScaleYear(PayScaleYear payScaleYear) {
        this.payScaleYear = payScaleYear;
    }

}
