/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.PayScale;

/**
 *
 * @author Mozahid
 */
@Repository
public interface PayScaleRepository extends JpaRepository<PayScale, Long>, JpaSpecificationExecutor<PayScale> {

    @Query("Select p from PayScale p where p.active=true order by displayOrder ASC")
    public List<PayScale> findAllActive();

    @Query("Select p from PayScale p  order by displayOrder ASC")
    public List<PayScale> findAllPayScale();

    @Query("Select p from PayScale p where p.payScaleYear.id=:payScaleYearId and p.postClass.id=:postClassId and p.grade.id=:gradeId and p.name=:name")
    public List<PayScale> findDuplicatePayScale(@Param("payScaleYearId") Long payScaleYearId, @Param("postClassId") Long postClassId, @Param("gradeId") Long gradeId, @Param("name") String name);
}
