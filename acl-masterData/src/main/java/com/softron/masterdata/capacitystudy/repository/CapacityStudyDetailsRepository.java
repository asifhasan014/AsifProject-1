package com.softron.masterdata.capacitystudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.capacitystudy.entity.CapacityStudyDetails;
@Repository
public interface CapacityStudyDetailsRepository extends JpaRepository<CapacityStudyDetails, Long> {

}
