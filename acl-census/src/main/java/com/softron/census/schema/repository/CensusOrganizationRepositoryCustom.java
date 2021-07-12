package com.softron.census.schema.repository;

import java.util.List;

import com.softron.census.schema.entity.CensusOrganization;

public interface CensusOrganizationRepositoryCustom {
	
	public List<CensusOrganization> findUserOrganizationByMinistry(Long moduleId, String userId);

}
