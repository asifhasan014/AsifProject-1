/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.CensusOffice;
import com.softron.census.schema.repository.CensusOfficeRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class CensusOfficeService {

    @Autowired
    private CensusOfficeRepository censusOfficeRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(CensusOffice censusOffice) {
        censusOfficeRepository.save(censusOffice);
    }

    @Transactional(readOnly = true)
    public List<CensusOffice> getAllActive() {
        List<CensusOffice> censusOffices = censusOfficeRepository.findAllActive();
        return censusOffices;
    }
    @Transactional(readOnly = true)
    public List<CensusOffice> getAllCensusOffice() {
        List<CensusOffice> censusOffices = censusOfficeRepository.findAllCensusOffice();
        return censusOffices;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        censusOfficeRepository.deleteById(id);
    }

    public CensusOffice getCensusOfficeById(Long id) {
        return censusOfficeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Census Office doesn't exist for id=" + id));
    }
}
