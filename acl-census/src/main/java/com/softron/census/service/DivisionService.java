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

import com.softron.census.schema.entity.Division;
import com.softron.census.schema.repository.DivisionRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Division division) {
        divisionRepository.save(division);
    }

    @Transactional(readOnly = true)
    public List<Division> getAllActive() {
        List<Division> divisions = divisionRepository.findAllActive();
        return divisions;
    }

    @Transactional(readOnly = true)
    public List<Division> getAllDistrict() {
        List<Division> districts = divisionRepository.findAll();
        return districts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        divisionRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Division> findDuplicateDivision(final String name) {
        return divisionRepository.findDuplicateDivision(name);
    }

    public Division getDivisionById(Long id) {
        return divisionRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Division doesn't exist for id=" + id));
    }
}
