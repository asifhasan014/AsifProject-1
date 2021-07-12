package com.softron.reporting.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.softron.common.entity.OrgUsersMapping;
import com.softron.common.service.UserMappingService;
import com.softron.reporting.entity.Report;
import com.softron.reporting.entity.ReportOrganization;
import com.softron.reporting.entity.ReportReceiver;
import com.softron.reporting.repository.ReportReceiverRepository;
import com.softron.reporting.repository.ReportingAttachmentsRepository;

@Service
public class ReportActionService {

    @Autowired
    private ReportReceiverRepository receiverRepository;

    @Autowired
    private UserMappingService userMappingService;

    @Autowired
    private ReportingAttachmentsRepository attRepo;

    @Async
    @Transactional(propagation = Propagation.REQUIRED)
    public void send(final Report rp) {
        final String reportId = rp.getReportId();
        attRepo.submit(reportId, rp.getSender());
        if (!CollectionUtils.isEmpty(rp.getAcks())) {
            sentToRecievers(reportId, getUsers(rp.getAcks()), true);
        }
        if (!CollectionUtils.isEmpty(rp.getRecipients())) {
        	sentToRecievers(reportId, getUsers(rp.getRecipients()), false);
        }
    }

    public void sentToRecievers(String reportId, List<OrgUsersMapping> users, boolean acknowledger) {
        List<ReportReceiver> receivers = new ArrayList<>();
        users.forEach(u -> {
            receivers.add(ReportReceiver.builder().reportId(reportId).orgId(u.getOrgId()).userId(u.getUserId()).acknowledger(acknowledger).submitted(false).build());
        });
        if (!receivers.isEmpty()) {
            receiverRepository.saveAll(receivers);
        }
    }

	private List<OrgUsersMapping> getUsers(List<ReportOrganization> orgs) {
        return userMappingService.findAllUserIdsByOrgIdsIn(getOrgIds(orgs));
    }

    private List<Long> getOrgIds(List<ReportOrganization> orgs) {
        return orgs.stream().map(ReportOrganization::getId).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void submit(String reportId, Long orgId, String userId) {
        attRepo.submit(reportId, orgId);
        receiverRepository.submitReport(reportId, orgId, userId, LocalDate.now());
    }

}
