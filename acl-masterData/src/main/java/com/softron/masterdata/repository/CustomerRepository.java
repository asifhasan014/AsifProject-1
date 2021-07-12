package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.qualitymodule.entity.QualityType;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	public List<Customer> findAllByActiveTrue();
	
	public List<Customer> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
	
	
	//Pageable jpql
	public List<Customer> findAllByNameAndActiveTrue(String Name,Pageable pageable);
	public List<Customer> findAllByNameAndActiveTrue(Pageable pageable);
	public long countByNameAndActiveTrue(String name);

}
