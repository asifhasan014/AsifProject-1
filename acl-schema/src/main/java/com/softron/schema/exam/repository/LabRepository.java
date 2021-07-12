/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.ExamOrganization;
import com.softron.schema.exam.entity.Lab;

/**
 *
 * @author Mozahid
 */
@Repository
public interface LabRepository extends JpaRepository<Lab, Long>, LabRepositoryCustom {

	@Query("Select a from Lab a")
	public List<Lab> findAllActive();

	@Query("Select l from Lab l where l.organization=:organization")
	public List<Lab> findLabByOrganization(@Param("organization") ExamOrganization organization);

	@Query("Select l from Lab l where l.name=:name and l.organization=:organization")
	public List<Lab> findDuplicateLab(@Param("name") String name, @Param("organization") ExamOrganization organization);

	/*@Query(value = "SELECT l.* FROM LAB l WHERE l.ORGANIZATION_ID IN "
			+ GET_USER_ORG_TREE_IDS_QUERY_NATIVE, nativeQuery = true)
	public List<Lab> findUserOrganizationLabs(@Param("moduleId") Long moduleId, @Param("userId") String userId);
	 */
}
