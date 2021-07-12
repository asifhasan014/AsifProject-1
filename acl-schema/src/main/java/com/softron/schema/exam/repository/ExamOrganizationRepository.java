package com.softron.schema.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.ExamOrganization;

@Repository
public interface ExamOrganizationRepository extends JpaRepository<ExamOrganization, Long>, ExamOrganizationRepositoryCustom {

	@Query("Select org from ExamOrganization org where org.active=true")
	public List<ExamOrganization> findAllActive();

	@Query("Select org from ExamOrganization org where org.orgType='MINISTRY'")
	public List<ExamOrganization> findOrgByMinistry();

	@Query("Select org from ExamOrganization org where org.parentId IS NULL")
	public List<ExamOrganization> findAllParentOrg();

	public List<ExamOrganization> findByParentId(Long parentId);

	public void deleteById(Long id);

	//@Query(value = ExamQueries.GET_USER_ORGS_QUERY_NATIVE, nativeQuery = true)
	//public List<ExamOrganization> findByUserId(@Param("moduleId") Long moduleId, @Param("userId") String userId);
}
