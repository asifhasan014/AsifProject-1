package com.softron.reporting.repository;

import com.softron.reporting.bo.ReplyAttachmentBO;
import com.softron.reporting.entity.ReportAttachments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingAttachmentsRepository extends JpaRepository<ReportAttachments, Long> {

    @Query("Select a from #{#entityName} a where a.report.reportId= :reportId and a.orgId = :orgId and a.draft = false")
    public List<ReportAttachments> findByReportIdAndOrgId(@Param("reportId") String reportId, @Param("orgId") Long orgId);

    @Query("Select a from #{#entityName} a where a.report.reportId= :reportId and a.orgId = :orgId and a.draft = true")
    public List<ReportAttachments> findByReportIdAndOrgIdDrafted(@Param("reportId") String reportId, @Param("orgId") Long orgId);

    @Modifying
    @Query("Update #{#entityName} a set a.draft = false where a.report.reportId = :reportId and a.orgId = :orgId")
    public void submit(@Param("reportId") String reportId, @Param("orgId") Long orgId);

    @Query(value = "SELECT distinct(o.NAME_ENGLISH) as org, r.ORG_ID as orgId, a.ID as id, r.IS_SUBMITTED as submitted, r.SUBMISSION_DATE as submittedOn, a.ATTACHMENT_NAME as name, a.ATTACHED_FILE as path "
            + "FROM OR_REPORT_RECEIVERS r "
            + "INNER JOIN  OR_ORGANIZATIONS o on r.ORG_ID = o.ID "
            + "LEFT OUTER JOIN OR_ATTACHMENTS a on (r.REPORT_ID = a.REPORT_ID AND r.ORG_ID = a.ORG_ID) "
            + "WHERE r.REPORT_ID = :reportId and r.IS_ACKNOWLEDGER = :flag", nativeQuery = true)
    public List<ReplyAttachmentBO> getReplyAttachments(@Param("reportId") String reportId, @Param("flag") boolean flag);
    
}
