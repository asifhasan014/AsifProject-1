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

import com.softron.census.schema.entity.CensusOffice;
import com.softron.census.schema.entity.CensusOfficeHierarchy;
import com.softron.census.schema.filter.CensusOfficeHierarchyFilter;
import com.softron.census.schema.repository.CensusOfficeHierarchyRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class CensusOfficeHierarchyService {

    @Autowired
    private CensusOfficeHierarchyRepository censusOfficeHierarchyRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(CensusOfficeHierarchy censusOfficeHierarchy) {
        censusOfficeHierarchyRepository.save(censusOfficeHierarchy);
    }

    @Transactional(readOnly = true)
    public List<CensusOfficeHierarchy> getAllActive() {
        List<CensusOfficeHierarchy> censusOfficeHierarchies = censusOfficeHierarchyRepository.findAllActive();
        return censusOfficeHierarchies;
    }
    @Transactional(readOnly = true)
    public List<CensusOfficeHierarchy> getAllCensusOfficeHierarchy() {
        List<CensusOfficeHierarchy> censusOfficeHierarchies = censusOfficeHierarchyRepository.findAllCensusOfficeHierarchy();
        return censusOfficeHierarchies;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        censusOfficeHierarchyRepository.deleteById(id);
    }

    public CensusOfficeHierarchy getCensusOfficeHierarchyById(Long id) {
        return censusOfficeHierarchyRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Census Office Hierarchy doesn't exist for id=" + id));
    }

    @Transactional(readOnly = true)
    public List<CensusOfficeHierarchy> getAllCensusOfficeHierarchyFilter(CensusOffice censusOffice) {
        CensusOfficeHierarchy censusOfficeHierarchy = new CensusOfficeHierarchy();
        censusOfficeHierarchy.setCensusOffice(censusOffice);
        Specification<CensusOfficeHierarchy> specification = new CensusOfficeHierarchyFilter(censusOfficeHierarchy);
        List<CensusOfficeHierarchy> result = censusOfficeHierarchyRepository.findAll(specification);
        return result;
    }
}
