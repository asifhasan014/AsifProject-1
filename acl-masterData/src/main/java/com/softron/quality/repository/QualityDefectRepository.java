package com.softron.quality.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.qualitymodule.entity.QualityDefect;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
@Repository
public interface QualityDefectRepository extends JpaRepository<QualityDefect, Long> {

	public List<QualityDefect> findAllByActiveTrue();
	
//	@Query(value="UPDATE quality_defect SET active = 0 WHERE qualitytransaction_id=:id",nativeQuery = true)
//	public void setQualityTransection(@Param("id") Long id);
	
}
