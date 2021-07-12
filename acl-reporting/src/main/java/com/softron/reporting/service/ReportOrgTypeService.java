package com.softron.reporting.service;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.reporting.entity.ReportOrgType;
import com.softron.reporting.repository.ReportingOrgTypeRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportOrgTypeService {

    @Autowired
    private ReportingOrgTypeRepository orgTypeRepository;

    public List<ReportOrgType> getActiveOrgTypes() {
        return orgTypeRepository.findAllActive();
    }

    public List<ReportOrgType> getAllOrgTypes() {
        return orgTypeRepository.findAll();
    }

    public ReportOrgType getOrgTypeById(Long id) {
        return orgTypeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("OrgType doesn't exist for id=" + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrgType(ReportOrgType orgType) {
        orgTypeRepository.save(orgType);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrgType(Long id) {
        orgTypeRepository.deleteById(id);
    }

}
