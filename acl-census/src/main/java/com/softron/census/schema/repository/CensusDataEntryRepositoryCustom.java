package com.softron.census.schema.repository;

import java.util.List;

import com.softron.census.schema.entity.CensusDataEntry;

public interface CensusDataEntryRepositoryCustom {

	public List<CensusDataEntry> findAllCensusDataEntryByUserOrg(Long moduleId, String userId);

}
