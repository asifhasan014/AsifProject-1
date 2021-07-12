package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public List<Employee> findAllByActiveTrue();
	
	public List<Employee> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
	
}
