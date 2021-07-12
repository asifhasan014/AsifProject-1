package com.softron.admin.service;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.Status;
import com.softron.schema.admin.repository.StatusRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusService {
    
    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getActiveStatus() {
        return statusRepository.findAllActive();
    }

    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    public List<Status> getActiveStatus(CaseFormType caseFormType) {
        return statusRepository.findAllActiveByFormType(caseFormType);
    }

    public List<Status> getAllStatus(CaseFormType caseFormType) {
        return statusRepository.findAllByFormType(caseFormType);
    }

    public Status getStatusById(Long id) {
        return statusRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Status doesn't exist for id=" + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStatus(Status status) {
        statusRepository.save(status);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }
}
