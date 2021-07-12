package com.softron.schema.legal.repository;

import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.Court;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    @Query("Select c from Court c where c.active=true")
    public List<Court> findAllActive();

    @Query("Select c from Court c where c.caseFormType = :caseFormType")
    public List<Court> findAllByFormType(@Param("caseFormType") CaseFormType caseFormType);

    @Query("Select c from Court c where c.active=true and c.caseFormType = :caseFormType")
    public List<Court> findAllActiveByFormType(@Param("caseFormType") CaseFormType caseFormType);

    public void deleteById(Long id);

}
