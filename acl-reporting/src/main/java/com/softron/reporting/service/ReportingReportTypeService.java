package com.softron.reporting.service;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.reporting.entity.ReportType;
import com.softron.reporting.repository.ReportingReportTypeRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportingReportTypeService {

    @Autowired
    private ReportingReportTypeRepository reportTypeRepository;

    public List<ReportType> getActiveReportTypes() {
        return reportTypeRepository.findAllActive();
    }

    public List<ReportType> getAllReportTypes() {
        return reportTypeRepository.findAll();
    }

    public ReportType getReportTypeById(Long id) {
        return reportTypeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("OrgType doesn't exist for id=" + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveReportType(ReportType reportType) {
        reportTypeRepository.save(reportType);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReportType(Long id) {
        reportTypeRepository.deleteById(id);
    }
}
