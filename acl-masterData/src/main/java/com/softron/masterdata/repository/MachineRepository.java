package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long>{

	public List<Machine> findAllByActiveTrue();
}
