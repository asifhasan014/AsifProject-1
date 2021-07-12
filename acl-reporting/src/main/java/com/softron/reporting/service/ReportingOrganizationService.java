package com.softron.reporting.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.transferobjects.MapTO;
import com.softron.core.domain.RequestContextData;
import com.softron.reporting.entity.ReportOrganization;
import com.softron.reporting.repository.ReportingOrganizationRepository;

@Service
public class ReportingOrganizationService {
	
	@Resource(name = "requestContextData")
	private RequestContextData requestContextData;

	@Autowired
	private ReportingOrganizationRepository orgRepository;

	public List<ReportOrganization> getActiveOrganizations() {
		return orgRepository.findAllActive();
	}

	public List<ReportOrganization> getAllOrganizations() {
		return orgRepository.findAll();
	}

	public ReportOrganization getOrganizationById(Long id) {
		return orgRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Organization doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganization(ReportOrganization org) {
		orgRepository.save(org);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOrganization(Long id) {
		orgRepository.deleteById(id);
	}

	public MapTO getByUserId(String userId) {
		ReportOrganization org = orgRepository.findByUserId(requestContextData.getModuleId().get(), userId).orElseGet(ReportOrganization::new);
		return MapTO.builder().id(org.getId()).name(org.getNameEng()).nameBn(org.getNameBang()).build();
	}

}
