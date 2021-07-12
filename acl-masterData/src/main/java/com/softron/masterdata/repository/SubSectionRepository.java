package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softron.schema.admin.entity.masterdata.SubSection;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface SubSectionRepository extends JpaRepository<SubSection, Long>{
	

}
