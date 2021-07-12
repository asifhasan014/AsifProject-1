package com.softron.reporting.repository;

import com.softron.reporting.entity.ReportType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingReportTypeRepository extends JpaRepository<ReportType, Long> {

    @Query("Select r from #{#entityName} r where r.active=true")
    public List<ReportType> findAllActive();

    @Query("Select r.name from #{#entityName} r where r.id = :id")
    public String getNameById(@Param("id") Long id);

}
