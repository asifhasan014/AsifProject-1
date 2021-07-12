package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long>{

	public List<Section> findAllByActiveTrue();
	
	public List<Section> findAllByParentId(Long id);
	public List<Section> findAllByParentAndActiveTrue(Section parent);
	public List<Section> findAllByPlantAndActiveTrue(Plant plant);
	
	public List<Section> findAllByOrganizationId(Long orgId);
	
	@Query(value="SELECT * FROM `section` WHERE active=true and parent_id is null",nativeQuery = true)
	public List<Section> findSectionWithoutSubSection();
	
//	@Query(value="SELECT * FROM `section` WHERE active=true and parent_id is null and org_id IN (:orgIds)",nativeQuery = true)
//	public List<Section> findSectionWithoutSubSectionAndOrganizationIdIn(@Param("orgIds") List<Long> orgIds);
	
//	public List<Section> findAllByPlantAndActiveTrueAndParentIdIsNull(Plant plant);
	
//	public List<Section> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
