package com.softron.schema.legal.repository;

import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.CaseType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseTypeRepository extends JpaRepository<CaseType, Long> {

    @Query("Select c from CaseType c where c.active=true")
    public List<CaseType> findAllActive();

    @Query("Select c from CaseType c where c.caseFormType = :caseFormType")
    public List<CaseType> findAllByFormType(@Param("caseFormType") CaseFormType caseFormType);

    @Query("Select c from CaseType c where c.active=true and c.caseFormType = :caseFormType")
    public List<CaseType> findAllActiveByFormType(@Param("caseFormType") CaseFormType caseFormType);

    public void deleteById(Long id);

}
