package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface WorkProcessRepository extends JpaRepository<WorkProcess, Long>{
	
	public List<WorkProcess> findAllByActiveTrue();
	
	public List<WorkProcess> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
