package com.softron.reporting.service;

import static com.softron.reporting.contants.ErrorMessageContants.REPORT_NOT_FOUND_EX;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.service.UserMappingService;
import com.softron.common.transferobjects.MapTO;
import com.softron.common.utils.AuthUtil;
import com.softron.common.utils.DateUtils;
import com.softron.reporting.entity.Report;
import com.softron.reporting.entity.ReportOrganization;
import com.softron.reporting.repository.ReportReceiverRepository;
import com.softron.reporting.repository.ReportRepository;
import com.softron.reporting.repository.ReportingOrganizationRepository;
import com.softron.reporting.repository.ReportingReportTypeRepository;
import com.softron.reporting.to.ReportTO;

@Service
@Transactional(readOnly = true)
public class ReportingReportService {

    @Autowired
    private ReportRepository reportRepo;

    @Autowired
    private ReportingOrganizationRepository orgRepo;

    @Autowired
    private ReportReceiverRepository recRepo;

    @Autowired
    private ReportingReportTypeRepository typeRepo;

    @Autowired
    private UserMappingService userMappingService;

    @Autowired
    private ReportActionService reportActionService;

    public ReportTO get(final String reportId) {
        String userId = AuthUtil.getUserName();
        Report report = getReport(reportId);
        ReportTO reportTO = new ReportTO();
        BeanUtils.copyProperties(report, reportTO);
        reportTO.setAcks(getOrgIds(report.getAcks()));
        reportTO.setRecipients(getOrgIds(report.getRecipients()));
        reportTO.setTypeText(typeRepo.getOne(report.getType()).getName());
        reportTO.setSenderName(orgRepo.getOne(report.getSender()).getNameEng());
        reportTO.setMemoDate(DateUtils.toDateStr(report.getMemoDate()));
        reportTO.setOnlyAck(isUserAcknowledger(report.getAcks()));
        if (userMappingService.getUserOrgId(userId) == report.getSender()) {
            reportTO.setSent(!report.isDraft());
        } else {
            reportTO.setSent(recRepo.isSubmitted(reportId, userMappingService.getUserOrgId(userId)) > 0);
        }
        return reportTO;
    }

    private boolean isUserAcknowledger(List<ReportOrganization> acks) {
        Long orgId = userMappingService.getUserOrgId(AuthUtil.getUserName());
        return acks.stream().map(ReportOrganization::getId).filter(s -> s.equals(orgId)).count() > 0;
    }

    private List<Long> getOrgIds(List<ReportOrganization> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(ReportOrganization::getId).collect(Collectors.toList());
    }

    public List<MapTO> getRecipients(final String reportId) {
        //@formatter:off
        return getReport(reportId)
                .getRecipients()
                .stream()
                .map(r -> MapTO.builder()
                            .id(r.getId())
                            .name(r.getNameEng())
                            .nameBn(r.getNameBang())
                            .build())
                .collect(Collectors.toList());
    }
    
    public List<MapTO> getAcks(final String reportId) {
        //@formatter:off
        return getReport(reportId)
                .getAcks()
                .stream()
                .map(r -> MapTO.builder()
                            .id(r.getId())
                            .name(r.getNameEng())
                            .nameBn(r.getNameBang())
                            .build())
                .collect(Collectors.toList());
    }

    protected Report getReport(String reportId) {
        return reportRepo.findById(reportId).orElseThrow(() -> new NoRecordExistsException(REPORT_NOT_FOUND_EX.getMessage() + reportId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(final ReportTO reportTO) {
        Report report = new Report();
        if (reportTO.getReportId() != null) {
            report = reportRepo.findById(reportTO.getReportId()).orElseGet(Report::new);
        }
        BeanUtils.copyProperties(reportTO, report);
        report.setMemoDate(DateUtils.toLocalDate(reportTO.getMemoDate()));
        if(!CollectionUtils.isEmpty(reportTO.getAcks())) {
            report.setAcks(orgRepo.findByIdIn(reportTO.getAcks()));
        } else {
            report.setAcks(null);
        }
        
        if(!CollectionUtils.isEmpty(reportTO.getRecipients())) {
            report.setRecipients(orgRepo.findByIdIn(reportTO.getRecipients()));
        } else {
            report.setRecipients(null);
        }
        reportRepo.save(report);
        if(!report.isDraft()) {
            reportActionService.send(report);
        }
        reportTO.setReportId(report.getReportId());
    }
    
    public void submit(final String reportId) {
        String userId = AuthUtil.getUserName();
        Long orgId = userMappingService.getUserOrgId(userId);
        reportActionService.submit(reportId, orgId, userId);
    }

}
