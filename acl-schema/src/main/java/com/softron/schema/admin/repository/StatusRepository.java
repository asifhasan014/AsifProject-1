package com.softron.schema.admin.repository;

import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.Status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    @Query("Select s from Status s where s.active=true")
    public List<Status> findAllActive();

    @Query("Select s from Status s where s.caseFormType = :caseFormType")
    public List<Status> findAllByFormType(@Param("caseFormType") CaseFormType caseFormType);
    
    @Query("Select s from Status s where s.active=true and s.caseFormType = :caseFormType")
    public List<Status> findAllActiveByFormType(@Param("caseFormType") CaseFormType caseFormType);

    public void deleteById(Long id);

}