package com.softron.reporting.repository;

import com.softron.reporting.entity.ReportType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepotingReportTypeRepository extends JpaRepository<ReportType, Long> {

    @Query("Select r from #{#entityName} r where r.active=true")
    public List<ReportType> findAllActive();

}
