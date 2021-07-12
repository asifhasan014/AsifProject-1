/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.CensusDataEntryProject;
import com.softron.census.schema.filter.CensusDataEntryProjectFilter;
import com.softron.census.schema.repository.CensusDataEntryProjectRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class CensusDataEntryProjectService {

	@Autowired
	private CensusDataEntryProjectRepository censusDataEntryProjectRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(CensusDataEntryProject censusDataEntryProject) {
		censusDataEntryProjectRepository.save(censusDataEntryProject);
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntryProject> getAllActive() {
		List<CensusDataEntryProject> censusDataEntryProjects = censusDataEntryProjectRepository.findAllActive();
		return censusDataEntryProjects;
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntryProject> getAllCensusDataEntryProject() {
		List<CensusDataEntryProject> censusDataEntryProjects = censusDataEntryProjectRepository
				.findAllCensusDataEntryProject();
		return censusDataEntryProjects;
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntryProject> getAllCensusDataEntryProjectByUserOrg(Long moduleId, String userId) {
		return censusDataEntryProjectRepository.findAllCensusDataEntryProjectByUserOrg(moduleId, userId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		censusDataEntryProjectRepository.deleteById(id);
	}

	public CensusDataEntryProject getCensusDataEntryProjectById(Long id) {
		return censusDataEntryProjectRepository.findById(id).orElseThrow(
				() -> new NoRecordExistsException("Census Data Entry Project Data Entry doesn't exist for id=" + id));
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntryProject> getAllCensusDataEntryProjectFilter(
			CensusDataEntryProject censusDataEntryProject) {
		Specification<CensusDataEntryProject> specification = new CensusDataEntryProjectFilter(censusDataEntryProject);
		List<CensusDataEntryProject> result = censusDataEntryProjectRepository.findAll(specification);
		return result;
	}
}
