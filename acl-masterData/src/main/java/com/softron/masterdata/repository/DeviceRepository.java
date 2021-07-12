package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>{

	public List<Device> findAllByActiveTrue();
}
