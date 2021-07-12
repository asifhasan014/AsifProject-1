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

import com.softron.census.schema.entity.District;
import com.softron.census.schema.entity.Division;
import com.softron.census.schema.repository.DistrictRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    
   
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(District district) {
        districtRepository.save(district);
    }

    @Transactional(readOnly = true)
    public List<District> getAllActive() {
        List<District> districts = districtRepository.findAllActive();
        return districts;
    }
    @Transactional(readOnly = true)
    public List<District> getDistrictByDivision(Division division) {
        List<District> districts = districtRepository.findDistrictByDivision(division);
        return districts;
    }
    @Transactional(readOnly = true)
    public List<District> getAllDistrict() {
        List<District> districts = districtRepository.findAll();
        return districts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        districtRepository.deleteById(id);
    }
  
    @Transactional(propagation = Propagation.REQUIRED)
    public List<District> findDuplicateDistrict(final String name) {
       return districtRepository.findDuplicateDistrict(name);
    }

    public District getDistrictById(Long id) {
        return districtRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("District doesn't exist for id=" + id));
    }
}
