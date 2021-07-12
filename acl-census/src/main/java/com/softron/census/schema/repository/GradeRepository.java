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

import com.softron.census.schema.entity.Grade;

/**
 *
 * @author Mozahid
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("Select g from Grade g where g.active=true order by displayOrder ASC")
    public List<Grade> findAllActive();

    @Query("Select g from Grade g order by displayOrder ASC")
    public List<Grade> findAllGrade();

    @Query("Select g from Grade g where g.name=:name")
    public List<Grade> findDuplicateGrade(@Param("name") String name);

}
