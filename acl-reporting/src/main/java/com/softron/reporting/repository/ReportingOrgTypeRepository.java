package com.softron.reporting.repository;

import com.softron.reporting.entity.ReportOrgType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingOrgTypeRepository extends JpaRepository<ReportOrgType, Long> {

    @Query("Select o from #{#entityName} o where o.active=true")
    public List<ReportOrgType> findAllActive();

    public void deleteById(Long id);
    
}
