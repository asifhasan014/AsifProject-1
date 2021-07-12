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

import com.softron.census.schema.entity.District;
import com.softron.census.schema.entity.Division;

/**
 *
 * @author Mozahid
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("Select d from District d where d.active=true order by d.displayOrder ASC")
    public List<District> findAllActive();

    @Query("Select d from District d order by d.displayOrder ASC")
    public List<District> findAllDistrict();

    @Query("Select c from District c where c.division=:division")
    public List<District> findDistrictByDivision(@Param("division") Division division);

    @Query("Select d from District d where d.name=:name")
    public List<District> findDuplicateDistrict(@Param("name") String name);

}
