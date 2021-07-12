package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
	public List<Style> findAllByActiveTrue();
	
	public List<Style> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
