package com.softron.masterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	public List<Item> findAllByActiveTrue();
	
	public List<Item> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
	
}
