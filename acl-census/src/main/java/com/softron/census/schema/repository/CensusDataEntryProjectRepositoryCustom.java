package com.softron.census.schema.repository;

import java.util.List;

import com.softron.census.schema.entity.CensusDataEntryProject;

public interface CensusDataEntryProjectRepositoryCustom {

	public List<CensusDataEntryProject> findAllCensusDataEntryProjectByUserOrg(Long moduleId, String userId);

}
