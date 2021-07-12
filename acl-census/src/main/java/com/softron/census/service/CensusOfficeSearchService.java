/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.CensusOfficeSearch;
import com.softron.census.schema.filter.CensusOfficeSearchFilter;
import com.softron.census.schema.repository.CensusOfficeSearchRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class CensusOfficeSearchService {

    @Autowired
    private CensusOfficeSearchRepository censusOfficeSearchRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(CensusOfficeSearch officeSearch) {
        censusOfficeSearchRepository.save(officeSearch);
    }

    @Transactional(readOnly = true)
    public List<CensusOfficeSearch> getAllActive() {
        List<CensusOfficeSearch> officeSearches = censusOfficeSearchRepository.findAllActive();
        return officeSearches;
    }
    
    @Transactional(readOnly = true)
    public List<CensusOfficeSearch> getAllCensusOfficeSearch() {
        List<CensusOfficeSearch> officeSearches = censusOfficeSearchRepository.findAllCensusOfficeSearch();
        return officeSearches;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        censusOfficeSearchRepository.deleteById(id);
    }

    public CensusOfficeSearch getOfficeSearchById(Long id) {
        return censusOfficeSearchRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Office Search doesn't exist for id=" + id));
    }

    @Transactional(readOnly = true)
    public List<CensusOfficeSearch> getAllOfficeSearch(String name) {
        CensusOfficeSearch censusOfficeSearch = new CensusOfficeSearch();
        censusOfficeSearch.setOfficeName(name);
        Specification<CensusOfficeSearch> specification = new CensusOfficeSearchFilter(censusOfficeSearch);
        List<CensusOfficeSearch> result = censusOfficeSearchRepository.findAll(specification);
        return result;
    }
}
