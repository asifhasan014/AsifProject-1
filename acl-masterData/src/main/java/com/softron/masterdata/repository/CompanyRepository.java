package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

	public List<Company> findAllByActiveTrue();
	
//	public List<Company> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
	
}
