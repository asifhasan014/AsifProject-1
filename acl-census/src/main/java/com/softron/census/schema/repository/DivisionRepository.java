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

import com.softron.census.schema.entity.Division;

/**
 *
 * @author Mozahid
 */
@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    @Query("Select d from Division d where d.active=true order by d.displayOrder ASC")
    public List<Division> findAllActive();
   
    @Query("Select d from Division d order by d.displayOrder ASC")
    public List<Division> findAllDivision();
    
    @Query("Select d from Division d where d.name=:name")
    public List<Division> findDuplicateDivision(@Param("name") String name);

}
