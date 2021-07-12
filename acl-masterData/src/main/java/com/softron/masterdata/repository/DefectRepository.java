package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long>{

	public List<Defect> findAllByActiveTrue();
	
	public List<Defect> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
