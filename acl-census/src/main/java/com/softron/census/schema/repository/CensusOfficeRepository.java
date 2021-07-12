/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.CensusOffice;

/**
 *
 * @author Mozahid
 */
@Repository
public interface CensusOfficeRepository extends JpaRepository<CensusOffice, Long> {

    @Query("Select c from CensusOffice c where c.active=true")
    public List<CensusOffice> findAllActive();

    @Query("Select c from CensusOffice c ")
    public List<CensusOffice> findAllCensusOffice();
}
