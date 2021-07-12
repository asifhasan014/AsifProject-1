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

import com.softron.census.schema.constant.OrgTypeEnum;
import com.softron.census.schema.entity.CensusOrganization;

/**
 *
 * @author Mozahid
 */
@Repository
public interface CensusOrganizationRepository extends JpaRepository<CensusOrganization, Long>, CensusOrganizationRepositoryCustom {

    @Query("Select c from CensusOrganization c where c.active = true")
    public List<CensusOrganization> findAllActive();

    @Query("Select c from CensusOrganization c")
    public List<CensusOrganization> findAllCensusOrganization();

    @Query("Select c from CensusOrganization c where c.orgType= 'MINISTRY'")
    public List<CensusOrganization> findAllMinistry();

    @Query("Select c from CensusOrganization c where c.orgType=:orgType and c.parentId=:parentId")
    public List<CensusOrganization> findByOrgTypeAndparentId(@Param("parentId") Long parentId, @Param("orgType") OrgTypeEnum orgType);

    @Query("Select c from CensusOrganization c where c.nameEng=:nameEng and c.orgType=:orgType and c.parentId=:id ")
    public List<CensusOrganization> findDuplicateCensusOrganization(@Param("nameEng") String nameEng, @Param("orgType") OrgTypeEnum orgType, @Param("id") Long id);
    
}
