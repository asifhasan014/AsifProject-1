/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import com.softron.census.domain.CensusPeriodTO;
import com.softron.census.schema.entity.CensusPeriod;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Mozahid
 */
public class CensusPeriodHelper {

    public static CensusPeriod convertCensusPeriodModelToCensusPeriod(CensusPeriodTO censusPeriodTO) throws ParseException {
        CensusPeriod censusPeriod = new CensusPeriod();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        censusPeriod.setActive(censusPeriodTO.isActive());
        censusPeriod.setEndDate(Instant.ofEpochMilli(dateFormat.parse(censusPeriodTO.getEndDate()).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        censusPeriod.setId(censusPeriodTO.getId());
        censusPeriod.setStartDate(Instant.ofEpochMilli(dateFormat.parse(censusPeriodTO.getStartDate()).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        censusPeriod.setLastDateDataEntry(Instant.ofEpochMilli(dateFormat.parse(censusPeriodTO.getLastDateDataEntry()).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        censusPeriod.setCensusPeriod(censusPeriodTO.getCensusPeriod());
        censusPeriod.setPayScaleYear(censusPeriodTO.getPayScaleYear());
        return censusPeriod;
    }

    public static CensusPeriodTO convertCensusPeriodToCensusPeriodModel(CensusPeriod censusPeriod) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CensusPeriodTO censusPeriodTO = new CensusPeriodTO();
        censusPeriodTO.setActive(censusPeriod.isActive());
        Date endDate = Date.from(censusPeriod.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        censusPeriodTO.setEndDate(dateFormat.format(endDate));
        censusPeriodTO.setId(censusPeriod.getId());
        Date startDate = Date.from(censusPeriod.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        censusPeriodTO.setStartDate(dateFormat.format(startDate));
        Date lastDateData = Date.from(censusPeriod.getLastDateDataEntry().atStartOfDay(ZoneId.systemDefault()).toInstant());
        censusPeriodTO.setLastDateDataEntry(dateFormat.format(lastDateData));
        censusPeriodTO.setCensusPeriod(censusPeriod.getCensusPeriod());
        censusPeriodTO.setPayScaleYear(censusPeriod.getPayScaleYear());
        return censusPeriodTO;
    }

}
