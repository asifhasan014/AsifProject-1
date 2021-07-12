package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, Long>{
	public List<MachineType> findAllByActiveTrue();
	
//	public List<MachineType> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
