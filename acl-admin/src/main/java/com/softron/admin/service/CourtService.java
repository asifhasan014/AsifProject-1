package com.softron.admin.service;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.Court;
import com.softron.schema.legal.repository.CourtRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    public List<Court> getActiveCourts() {
        return courtRepository.findAllActive();
    }

    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    public List<Court> getActiveCourts(CaseFormType caseFormType) {
        return courtRepository.findAllActiveByFormType(caseFormType);
    }

    public List<Court> getAllCourts(CaseFormType caseFormType) {
        return courtRepository.findAllByFormType(caseFormType);
    }

    public Court getCourtById(Long id) {
        return courtRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Court doesn't exist for id=" + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCourt(Court court) {
        courtRepository.save(court);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCourt(Long id) {
        courtRepository.deleteById(id);
    }
}
