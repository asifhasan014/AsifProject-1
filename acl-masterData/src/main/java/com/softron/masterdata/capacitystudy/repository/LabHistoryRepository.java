package com.softron.masterdata.capacitystudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.capacitystudy.entity.LabHistory;
@Repository
public interface LabHistoryRepository extends JpaRepository<LabHistory, Long>{

}
