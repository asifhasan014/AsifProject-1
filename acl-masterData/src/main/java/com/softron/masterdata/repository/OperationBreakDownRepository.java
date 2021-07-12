package com.softron.masterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface OperationBreakDownRepository extends JpaRepository<OperationBreakDown, Long>{
	
	public List<OperationBreakDown> findAllByActiveTrue();
	
	public List<OperationBreakDown> findAllByStyleIdAndActiveTrue(Long styleId);
	
	public List<OperationBreakDown> findAllByStyleAndActiveTrue(Style style);
	
	@Query(value = "SELECT allowance from operation_break_down where id=:opbdId",nativeQuery = true)
	public float findAllowanceById(@Param("opbdId") Long opbdId);
	
//	public List<OperationBreakDown> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
