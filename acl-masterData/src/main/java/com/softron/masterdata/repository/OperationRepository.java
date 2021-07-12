package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{

	public List<Operation> findAllByActiveTrue();
	
	public List<Operation> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
