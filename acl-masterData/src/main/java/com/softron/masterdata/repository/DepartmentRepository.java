package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

	public List<Department> findAllByActiveTrue();
	
//	public List<Department> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
