package com.softron.reporting.repository;

import com.softron.reporting.entity.ReportReceiver;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportReceiverRepository extends JpaRepository<ReportReceiver, Long> {

    @Modifying
    @Query("Update #{#entityName} r set r.submitted = true, r.submittedOn = :submittedOn where r.reportId = :reportId and r.orgId = :orgId and r.userId = :userId")
    public void submitReport(@Param("reportId") String reportId, @Param("orgId") Long orgId, @Param("userId") String userId, @Param("submittedOn") LocalDate submittedOn);

    @Query("Select count(r.id) from #{#entityName} r where r.reportId = :reportId and r.orgId = :orgId and r.submitted = true")
    public int isSubmitted(@Param("reportId") String reportId, @Param("orgId") Long orgId);

}
