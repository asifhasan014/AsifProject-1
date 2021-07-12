package com.softron.masterdata.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;

@Repository
public interface TargetAndManpowerRepository extends JpaRepository<TargetAndManpower, Long>{
	public List<TargetAndManpower> findAllByActiveTrue();
	
	@Query(value="SELECT * FROM `target_and_manpower` WHERE org_id=:orgId AND date BETWEEN :startDate AND :endDate",nativeQuery = true)
	public List<TargetAndManpower> findAllByStartAndEndDate(@Param("orgId") Long orgId,@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
//	public List<TargetAndManpower> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
	
	@Query(value="SELECT * FROM `target_and_manpower` WHERE org_id=:orgId AND date=:presentDate",nativeQuery = true)
	public TargetAndManpower findByOrganizationAndDate(@Param("orgId") Long orgId,@Param("presentDate") String presentDate);
}
