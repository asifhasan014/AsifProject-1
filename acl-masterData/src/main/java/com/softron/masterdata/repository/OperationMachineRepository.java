package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.Plant;

public interface OperationMachineRepository extends JpaRepository<OperationMachine, Long>{
	public List<OperationMachine> findAllByActiveTrue();

	public List<OperationMachine> findAllByOperationBreakDownAndActiveTrue(OperationBreakDown operationBreakDown);

//	public List<OperationMachine> findAllByOperationBreakDownAndActiveTrueAndSectionId(OperationBreakDown operationBreakDown, Long sectionId);
	
	public List<OperationMachine> findAllByOperationBreakDownAndActiveTrueAndOrganizationId(OperationBreakDown operationBreakDown, Long orgId);

//	@Query(value = "SELECT * FROM operation_machine WHERE active=true AND operationbreakdown_id=:opbdId and section_id=:sectionId",nativeQuery = true)
//	public List<OperationMachine> findOperationMachineList(@Param("opbdId") Long opbdId,@Param("sectionId") Long sectionId);

//	public List<OperationMachine> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
}
