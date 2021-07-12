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

import com.softron.census.schema.entity.ProjectType;

/**
 *
 * @author Mozahid
 */
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {

    @Query("Select p from ProjectType p where p.active=true order by p.displayOrder ASC")
    public List<ProjectType> findAllActive();

    @Query("Select p from ProjectType p order by p.displayOrder ASC")
    public List<ProjectType> findAllDistrict();

    @Query("Select p from ProjectType p where p.name=:name")
    public List<ProjectType> findDuplicateProjectType(@Param("name") String name);

}
