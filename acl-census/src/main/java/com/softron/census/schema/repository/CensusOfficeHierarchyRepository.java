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
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.CensusOfficeHierarchy;

/**
 *
 * @author Mozahid
 */
@Repository
public interface CensusOfficeHierarchyRepository extends JpaRepository<CensusOfficeHierarchy, Long>, JpaSpecificationExecutor<CensusOfficeHierarchy> {

    @Query("Select c from CensusOfficeHierarchy c where c.active=true")
    public List<CensusOfficeHierarchy> findAllActive();

    @Query("Select c from CensusOfficeHierarchy c")
    public List<CensusOfficeHierarchy> findAllCensusOfficeHierarchy();
}
