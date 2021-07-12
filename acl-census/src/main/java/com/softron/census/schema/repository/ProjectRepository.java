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

import com.softron.census.schema.entity.Project;

/**
 *
 * @author Mozahid
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query("Select p from Project p where p.active=true")
    public List<Project> findAllActive();

    @Query("Select p from Project p")
    public List<Project> findAllProject();

    @Query("Select p from Project p where p.name=:name and censusOrganization.id=:id")
    public List<Project> findDuplicateProject(@Param("name") String name,@Param("id") Long id);
}
