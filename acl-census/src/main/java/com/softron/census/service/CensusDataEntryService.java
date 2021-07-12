/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.domain.CensusSearchTO;
import com.softron.census.domain.OfficeTypeTO;
import com.softron.census.domain.OrgTypeTO;
import com.softron.census.schema.constant.OfficeTypeEnum;
import com.softron.census.schema.constant.OrgTypeEnum;
import com.softron.census.schema.constant.UrlEnum;
import com.softron.census.schema.entity.CensusDataEntry;
import com.softron.census.schema.entity.CensusOrganization;
import com.softron.census.schema.entity.Division;
import com.softron.census.schema.filter.CensusDataEntryFilter;
import com.softron.census.schema.repository.CensusDataEntryRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class CensusDataEntryService {

	@Autowired
	private CensusDataEntryRepository censusDataEntryRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(CensusDataEntry censusDataEntry) {
		censusDataEntryRepository.save(censusDataEntry);
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntry> getAllActive() {
		List<CensusDataEntry> censusDataEntrys = censusDataEntryRepository.findAllActive();
		return censusDataEntrys;
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntry> getAllCensusDataEntry() {
		List<CensusDataEntry> censusDataEntrys = censusDataEntryRepository.findAllCensusDataEntry();
		return censusDataEntrys;
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntry> getAllCensusDataEntryByUserOrg(Long moduleId, String userId) {
		return censusDataEntryRepository.findAllCensusDataEntryByUserOrg(moduleId, userId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		censusDataEntryRepository.deleteById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<OrgTypeTO> getAllOrgType() {
		List<OrgTypeTO> orgTypeTOList = new ArrayList<>();
		for (OrgTypeEnum typeEnum : OrgTypeEnum.getAllOrgType()) {
			orgTypeTOList.add(new OrgTypeTO(typeEnum.getValue(), typeEnum.name()));
		}
		return orgTypeTOList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<OfficeTypeTO> getAllOfficeType() {
		List<OfficeTypeTO> officeTypeTOs = new ArrayList<>();
		for (OfficeTypeEnum officeTypeEnum : OfficeTypeEnum.getAllOfficeType()) {
			officeTypeTOs.add(new OfficeTypeTO(officeTypeEnum.getValue(), officeTypeEnum.toString()));
		}
		return officeTypeTOs;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<OfficeTypeTO> getAllUrl() {
		List<OfficeTypeTO> officeTypeTOs = new ArrayList<>();
		for (UrlEnum urlEnum : UrlEnum.getAllUrl()) {
			officeTypeTOs.add(new OfficeTypeTO(urlEnum.getName(), urlEnum.getValue()));
		}
		return officeTypeTOs;
	}

	public CensusDataEntry getCensusDataEntryById(Long id) {
		return censusDataEntryRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Census Data Entry doesn't exist for id=" + id));
	}

	@Transactional(readOnly = true)
	public List<CensusDataEntry> getAllCensusDataEntryFilter(CensusSearchTO censusSearchTO) {
		Specification<CensusDataEntry> specification = new CensusDataEntryFilter(censusSearchTO);
		List<CensusDataEntry> result = censusDataEntryRepository.findAll(specification);
		return result;
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseMinistriesAndDivisionForChart(String year) {
		return censusDataEntryRepository.getTotalOfClassWiseMinistriesAndDivisionForChart(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseDepartmentAndDirectorateForChart(String year) {
		return censusDataEntryRepository.getTotalOfClassWiseDepartmentAndDirectorateForChart(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getSummaryOfManpower() {
		return censusDataEntryRepository.getSummaryOfManpower();
	}

	@Transactional(readOnly = true)
	public List<Object[]> getSummaryOfManpower(String year) {
		return censusDataEntryRepository.getSummaryOfManpower(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfSummaryOfManpower(String year) {
		return censusDataEntryRepository.getTotalOfSummaryOfManpower(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseDivisionalCommAndDeputyCommForChart(String year) {
		return censusDataEntryRepository.getTotalOfClassWiseDivisionalCommAndDeputyCommForChart(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseAutonomousBodiesAndCorporationForChart(String year) {
		return censusDataEntryRepository.getTotalOfClassWiseAutonomousBodiesAndCorporationForChart(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClassWiseStaticsOfProjectPersonnelData() {
		return censusDataEntryRepository.getClassWiseStaticsOfProjectPersonnelData();
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClassWiseStaticsOfProjectPersonnel11Data(String year) {
		return censusDataEntryRepository.getClassWiseStaticsOfProjectPersonnel11Data(year);
	}

	@Transactional(readOnly = true)
	public List<CensusOrganization> getAllCesnusOrganization() {
		return censusDataEntryRepository.getAllCensusOrganization();
	}

	@Transactional(readOnly = true)
	public List<Division> getAllDivision() {
		return censusDataEntryRepository.getAllDivision();
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClassWiseStatisticsOfDepartmentAndDirectorateByParentName(String parent, String year) {
		return censusDataEntryRepository.getClassWiseStatisticsOfDepartmentAndDirectorateByParentName(parent, year);
	}

	public List<Object[]> getClassWiseDivisionalCommDeputyCommByParentName(String parent, String year) {
		return censusDataEntryRepository.getClassWiseDivisionalCommDeputyCommByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByParentName(String parent,
			String year) {
		return censusDataEntryRepository
				.getTotalOfClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseStatisticsOfDepartmentAndDirectorate(String parent, String year) {
		return censusDataEntryRepository.getTotalOfClassWiseStatisticsOfDepartmentAndDirectorate(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getCivilOfficerAndStaffDataByYear(String year) {
		return censusDataEntryRepository.getCivilOfficerAndStaffDataByYear(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClassWiseStaticsOfFemaleOfficersAndStaff11Data(String year) {
		return censusDataEntryRepository.getClassWiseStaticsOfFemaleOfficersAndStaff11Data(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfCivilOfficerAndStaffByYear(String year) {
		return censusDataEntryRepository.getTotalOfCivilOfficerAndStaffByYear(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseStaticsOfFemaleOfficersAndStaff11Data(String year) {
		return censusDataEntryRepository.getTotalOfClassWiseStaticsOfFemaleOfficersAndStaff11Data(year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClassWiseStatisticsOfAutonomousBodiesCorporationByParentName(String parent, String year) {
		return censusDataEntryRepository.getClassWiseStatisticsOfAutonomousBodiesCorporationByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseStatisticsOfAutonomousBodiesCorporation(String parent, String year) {
		return censusDataEntryRepository.getTotalOfClassWiseStatisticsOfAutonomousBodiesCorporation(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClassWiseMinistriesAndDivisionByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfClassWiseMinistriesAndDivisionByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClassWiseMinistriesAndDivision11ByParentName(String parent, String year) {
		return censusDataEntryRepository.getClassWiseMinistriesAndDivision11ByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getMaleAndFemaleOfMinistryDivisionByParentName(String parent, String year) {
		return censusDataEntryRepository.getMaleAndFemaleOfMinistryDivisionByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfMaleAndFemaleOfMinistryDivisionByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfMaleAndFemaleOfMinistryDivisionByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByName(String parent, String year) {
		return censusDataEntryRepository.getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClass1OfficerOfDepartmentAndDirectorateByPayscaleByName(String parent, String year) {
		return censusDataEntryRepository.getClass1OfficerOfDepartmentAndDirectorateByPayscaleByName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClass1OfficerOfMinistriesDivisionByPayscaleByParentName(String parent,
			String year) {
		return censusDataEntryRepository.getTotalOfClass1OfficerOfMinistriesDivisionByPayscaleByParentName(parent,
				year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClass1OfficerOfMinistriesDivisionByPayscaleByName(String parent, String year) {
		return censusDataEntryRepository.getClass1OfficerOfMinistriesDivisionByPayscaleByName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClass1OfficerOfDivCommDepCommByPayscaleByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfClass1OfficerOfDivCommDepCommByPayscaleByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getClass1OfficerOfDivCommDepCommByPayscaleByName(String parent, String year) {
		return censusDataEntryRepository.getClass1OfficerOfDivCommDepCommByPayscaleByName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfDivisionalCommDeputyCommByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfDivisionalCommDeputyCommByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfMaleAndFemaleOfAutomousAndCorporationByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfMaleAndFemaleOfAutomousAndCorporationByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getMaleAndFemaleOfAutomousAndCorporationByParentName(String parent, String year) {
		return censusDataEntryRepository.getMaleAndFemaleOfAutomousAndCorporationByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getMaleAndFemaleOfDivCommDeputyCommByParentNameByParentName(String parent, String year) {
		return censusDataEntryRepository.getMaleAndFemaleOfDivCommDeputyCommByParentNameByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfMaleAndFemaleDivCommDeputyCommByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfMaleAndFemaleDivCommDeputyCommByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfClass1OfficerOfDepartmentAndDirectorateByPayscaleByParentName(String parent,
			String year) {
		return censusDataEntryRepository.getTotalOfClass1OfficerOfDepartmentAndDirectorateByPayscaleByParentName(parent,
				year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getTotalOfMaleAndFemaleOfDepartmentAndDirectorateByParentName(String parent, String year) {
		return censusDataEntryRepository.getTotalOfMaleAndFemaleOfDepartmentAndDirectorateByParentName(parent, year);
	}

	@Transactional(readOnly = true)
	public List<Object[]> getMaleAndFemaleOfDepartmentAndDirectorateByParentName(String parent, String year) {
		return censusDataEntryRepository.getMaleAndFemaleOfDepartmentAndDirectorateByParentName(parent, year);
	}
}
