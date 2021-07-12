package com.softron.production.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softron.schema.admin.entity.production.ReportLayout;

public interface ReportLayoutRepository extends JpaRepository<ReportLayout, Long> {
	public List<ReportLayout> findAllByActiveTrue();
	
	//@Query(value = "SELECT * FROM `report_layout` WHERE type=:typevalue AND active=true",nativeQuery = true)
	//public List<ReportLayout> findAllByType(@Param("typevalue") String typevalue);
	
	public List<ReportLayout> findAllByTypeAndActiveTrue(String typevalue);
	
	
}
