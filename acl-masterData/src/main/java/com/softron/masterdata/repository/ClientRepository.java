package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	public List<Client> findAllByActiveTrue();
	public List<Client> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);

}