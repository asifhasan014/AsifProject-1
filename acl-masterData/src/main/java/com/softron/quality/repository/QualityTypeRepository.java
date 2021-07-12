package com.softron.quality.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.qualitymodule.entity.QualityTransaction;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface QualityTypeRepository extends JpaRepository<QualityType, Long>{
	public List<QualityType> findAllByActiveTrue();
	
	
}
