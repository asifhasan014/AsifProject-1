package com.softron.census.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.domain.CensusPeriodTO;
import com.softron.census.helper.CensusPeriodHelper;
import com.softron.census.schema.entity.CensusPeriod;
import com.softron.census.schema.repository.CensusPeriodRepository;
import com.softron.common.exceptions.NoRecordExistsException;

@Service
public class CensusPeriodService {

    @Autowired
    private CensusPeriodRepository censusPeriodRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(CensusPeriod censusPeriod) {
        censusPeriodRepository.save(censusPeriod);
    }

    @Transactional(readOnly = true)
    public List<CensusPeriodTO> getAllActive() {
        List<CensusPeriod> censusPeriods = censusPeriodRepository.findAllActive();
        List<CensusPeriodTO> censusPeriodTOs = new ArrayList<>();
        if (censusPeriods != null && !censusPeriods.isEmpty()) {
            for (CensusPeriod censusPeriod : censusPeriods) {
                censusPeriodTOs.add(CensusPeriodHelper.convertCensusPeriodToCensusPeriodModel(censusPeriod));
            }
        }
        return censusPeriodTOs;
    }

    @Transactional(readOnly = true)
    public List<CensusPeriodTO> getAllCensusPeriod() {
        List<CensusPeriod> censusPeriods = censusPeriodRepository.findAllCensusPeriod();
        List<CensusPeriodTO> censusPeriodTOs = new ArrayList<>();
        if (censusPeriods != null && !censusPeriods.isEmpty()) {
            for (CensusPeriod censusPeriod : censusPeriods) {
                censusPeriodTOs.add(CensusPeriodHelper.convertCensusPeriodToCensusPeriodModel(censusPeriod));
            }
        }
        return censusPeriodTOs;
    }

    @Transactional(readOnly = true)
    public List<CensusPeriodTO> findAllCensusPeriodByOrder() {
        List<CensusPeriod> censusPeriods = censusPeriodRepository.findAllCensusPeriodByOrder();
        List<CensusPeriodTO> censusPeriodTOs = new ArrayList<>();
        if (censusPeriods != null && !censusPeriods.isEmpty()) {
            for (CensusPeriod censusPeriod : censusPeriods) {
                censusPeriodTOs.add(CensusPeriodHelper.convertCensusPeriodToCensusPeriodModel(censusPeriod));
            }
        }
        return censusPeriodTOs;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        censusPeriodRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CensusPeriod> findDuplicateCensusPeriod(final String name) {
        return censusPeriodRepository.findDuplicateCensusPeriod(name);
    }

    public CensusPeriod getCensusPeriodById(Long id) {
        return censusPeriodRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Census Period doesn't exist for id=" + id));
    }

}
