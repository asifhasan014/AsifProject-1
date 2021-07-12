package com.softron.masterdata.capacitystudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.capacitystudy.entity.WorkStudy;
@Repository
public interface WorkStudyRepository extends JpaRepository<WorkStudy , Long>{

}
