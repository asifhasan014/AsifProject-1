/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.PayScaleYear;

/**
 *
 * @author Mozahid
 */
@Repository
public interface PayScaleYearRepository extends JpaRepository<PayScaleYear, Long> {

    @Query("Select p from PayScaleYear p where p.active=true order by displayOrder ASC")
    public List<PayScaleYear> findAllActive();
  
    @Query("Select p from PayScaleYear p order by displayOrder ASC")
    public List<PayScaleYear> findAllPayScaleYear();
    
    @Query("Select p from PayScaleYear p where p.name=:name")
    public List<PayScaleYear> findDuplicatePayScaleYear(@Param("name") String name);

}
