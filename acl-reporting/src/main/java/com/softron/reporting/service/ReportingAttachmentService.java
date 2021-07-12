package com.softron.reporting.service;

import static com.softron.reporting.contants.ErrorMessageContants.RECORD_NOT_FOUND_EX;
import static com.softron.reporting.contants.ErrorMessageContants.REPORT_NOT_FOUND_EX;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.service.UserMappingService;
import com.softron.common.transferobjects.MapTO;
import com.softron.common.utils.AuthUtil;
import com.softron.reporting.bo.ReplyAttachmentBO;
import com.softron.reporting.entity.Report;
import com.softron.reporting.entity.ReportAttachments;
import com.softron.reporting.repository.ReportReceiverRepository;
import com.softron.reporting.repository.ReportRepository;
import com.softron.reporting.repository.ReportingAttachmentsRepository;
import com.softron.reporting.to.AttachmentTO;
import com.softron.reporting.to.ReplyAttachmentTO;

@Service
@Transactional(readOnly = true)
public class ReportingAttachmentService {

    @Autowired
    private ReportingAttachmentsRepository repository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportingFileService fileService;

    @Autowired
    private UserMappingService userMappingService;
    
    @Autowired
    private ReportingOrganizationService orgService;

    @Autowired
    private ReportReceiverRepository recRepo;

    public List<AttachmentTO> getSenderAttachments(final String reportId) {
        return this.getAttachments(reportId, getReport(reportId).getSender(), false);
    }

    public List<AttachmentTO> getOrgAttachments(final String reportId) {
        Long orgId = userMappingService.getUserOrgId(AuthUtil.getUserName());
        // if 0 then receiver has not submit the report.
        // Currently acknowledger can't see submitted attachments
        boolean notSubmitted = recRepo.isSubmitted(reportId, orgId) == 0;
        return this.getAttachments(reportId, orgId, notSubmitted);
    }

    protected List<AttachmentTO> getAttachments(final String reportId, final Long orgId, boolean drafted) {
        List<ReportAttachments> atts = Collections.emptyList();
        if (drafted) {
            atts = repository.findByReportIdAndOrgIdDrafted(reportId, orgId);
        } else {
            atts = repository.findByReportIdAndOrgId(reportId, orgId);
        }
        return atts.stream().map(entity -> {
            final AttachmentTO to = new AttachmentTO();
            BeanUtils.copyProperties(entity, to);
            return to;
        }).collect(toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(final String reportId, final AttachmentTO dataTO, final MultipartFile file) {
        final String fileName = fileService.uploadFile(file);
        dataTO.setAttachmentPath(fileName);
        final ReportAttachments entity = new ReportAttachments();
        BeanUtils.copyProperties(dataTO, entity);
        entity.setReport(getReport(reportId));
        MapTO userOrg = orgService.getByUserId(AuthUtil.getUserName());
        entity.setOrgId(userOrg.getId());
        entity.setDraft(true);
        repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        String fileName = getAttch(id).getAttachmentPath();
        repository.deleteById(id);
        fileService.deleteFile(fileName);
    }

    public Resource download(Long id) {
        final String fileName = getAttch(id).getAttachmentPath();
        return fileService.downloadFile(fileName);
    }
    
    public Resource downloadFiles(String reportId, Long orgId) throws IOException {
    	List<String> files = repository.findByReportIdAndOrgId(reportId, orgId).stream().map(ReportAttachments::getAttachmentPath).collect(Collectors.toList());
    	return fileService.downloadFiles(files);
    }

    private ReportAttachments getAttch(final Long id) {
        return repository.findById(id).orElseThrow(() -> new NoRecordExistsException(RECORD_NOT_FOUND_EX.getMessage() + id));
    }

    public Report getReport(String reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> new NoRecordExistsException(REPORT_NOT_FOUND_EX.getMessage() + reportId));
    }

    public List<ReplyAttachmentTO> getReplyAttachments(final String reportId) {
        List<ReplyAttachmentBO> list = repository.getReplyAttachments(reportId, false);
        List<ReplyAttachmentTO> replyAttchs = new ArrayList<>();
        Map<String, List<ReplyAttachmentTO>> groupedAttachments =  list.stream().map(a -> {
            ReplyAttachmentTO obj = new ReplyAttachmentTO();
            BeanUtils.copyProperties(a, obj);
            return obj;
        }).collect(Collectors.groupingBy(ReplyAttachmentTO::getOrg));
        
        groupedAttachments.forEach((key, value) -> {
        	final ReplyAttachmentTO first = value.get(0);
        	if(value.size() > 1) {
        		ReplyAttachmentTO attch = ReplyAttachmentTO.builder()
        				.org(key)
        				.name("Multiple Attachments")
        				.orgId(first.getOrgId())
        				.submitted(isSubmitted(value))
        				.submittedOn(getLatestDate(value))
        				.path(reportId + "_" + key + "_files.zip")
        				.multiple(true)
        				.build();
        		replyAttchs.add(attch);
        	} else {
        		replyAttchs.add(first);
        	}
        });
        return replyAttchs;
    }
    
    public Boolean isSubmitted(final List<ReplyAttachmentTO> attchs) {
    	return attchs.stream().filter(e -> e.getSubmitted()).count() > 0; 
    }
    
    public LocalDate getLatestDate(final List<ReplyAttachmentTO> attchs) {
    	final List<LocalDate> sortedDates = attchs.stream().map(ReplyAttachmentTO::getSubmittedOn).filter(e -> e != null).sorted().collect(Collectors.toList());
    	if(!sortedDates.isEmpty()) {
    		return sortedDates.get(sortedDates.size()-1);
    	}
    	return null;
    }

}
