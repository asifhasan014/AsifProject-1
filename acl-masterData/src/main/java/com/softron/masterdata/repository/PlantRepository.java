package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>{

	public List<Plant> findAllByActiveTrue();
	
//	public List<Plant> findAllByCompanyAndActiveTrue(Company company);
	
//	public List<Plant> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
