/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.domain.OfficeTypeTO;
import com.softron.census.schema.constant.OfficeTypeEnum;
import com.softron.census.schema.constant.OrgTypeEnum;
import com.softron.census.schema.entity.CensusOrganization;
import com.softron.census.schema.repository.CensusOrganizationRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class CensusOrganizationService {

    @Autowired
    private CensusOrganizationRepository censusOrganizationRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(CensusOrganization censusOrganization) {
        censusOrganizationRepository.save(censusOrganization);
    }

    @Transactional(readOnly = true)
    public List<CensusOrganization> getAllActive() {
        List<CensusOrganization> censusOrganizations = censusOrganizationRepository.findAllActive();
        return censusOrganizations;
    }
    
    @Transactional(readOnly = true)
    public List<CensusOrganization> getUserOrganizationByMinistry(Long moduleId, String userId) {
    	List<CensusOrganization> censusOrganizations = censusOrganizationRepository.findUserOrganizationByMinistry(moduleId, userId);
    	return censusOrganizations;
    }

    @Transactional(readOnly = true)
    public List<CensusOrganization> getAllCensusOrganization() {
        List<CensusOrganization> censusOrganizations = censusOrganizationRepository.findAllCensusOrganization();
        return censusOrganizations;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        censusOrganizationRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CensusOrganization> findDuplicateCensusOrganization(String nameEng, String orgType,Long parentId) {
        return censusOrganizationRepository.findDuplicateCensusOrganization(nameEng, OrgTypeEnum.valueOf(orgType),parentId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CensusOrganization> findAllByMinistry() {
        return censusOrganizationRepository.findAllMinistry();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CensusOrganization> findByOrgTypeAndparentId(String id, String orgType) {
        return censusOrganizationRepository.findByOrgTypeAndparentId(Long.valueOf(id), OrgTypeEnum.valueOf(orgType));
    }

    public CensusOrganization getCensusOrganizationById(Long id) {
        return censusOrganizationRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Census Organization doesn't exist for id=" + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OfficeTypeTO> getAllOfficeType() {
        List<OfficeTypeTO> officeTypeTOs = new ArrayList<>();
        for (OfficeTypeEnum officeTypeEnum : OfficeTypeEnum.getAllOfficeType()) {
            officeTypeTOs.add(new OfficeTypeTO(officeTypeEnum.getValue(), officeTypeEnum.toString()));
        }
        return officeTypeTOs;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OfficeTypeTO> getAllOfficeTypeByOrgType(String name) {
        List<OfficeTypeTO> officeTypeTOs = new ArrayList<>();
        for (OfficeTypeEnum officeTypeEnum : OfficeTypeEnum.getAllOfficeTypeByName(name)) {
            officeTypeTOs.add(new OfficeTypeTO(officeTypeEnum.getValue(), officeTypeEnum.name()));
        }
        return officeTypeTOs;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrgTypeEnum getOrgTypeById(Long id) {
        return OrgTypeEnum.getOrgTypeById(id);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OfficeTypeTO> getAllOrgType() {
        List<OfficeTypeTO> officeTypeTOs = new ArrayList<>();
        Map<String, OrgTypeEnum> map = OrgTypeEnum.mapObj1;
        for (Map.Entry<String, OrgTypeEnum> entry : map.entrySet()) {
            String key = entry.getKey();
            OrgTypeEnum value = entry.getValue();
            officeTypeTOs.add(new OfficeTypeTO(value.getValue(), String.valueOf(key)));
        }
        return officeTypeTOs;
    }
}
