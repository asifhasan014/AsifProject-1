package com.softron.admin.service;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.CaseType;
import com.softron.schema.legal.repository.CaseTypeRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CaseTypeService {

	@Autowired
	private CaseTypeRepository caseTypeRepository;

	public List<CaseType> getActiveCaseTypes() {
		return caseTypeRepository.findAllActive();
	}

	public List<CaseType> getAllCaseTypes() {
		return caseTypeRepository.findAll();
	}

	public List<CaseType> getActiveCaseTypes(CaseFormType caseFormType) {
		return caseTypeRepository.findAllActiveByFormType(caseFormType);
	}

	public List<CaseType> getAllCaseTypes(CaseFormType caseFormType) {
		return caseTypeRepository.findAllByFormType(caseFormType);
	}

	public CaseType getCaseTypeById(Long id) {
		return caseTypeRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("CaseType doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveCaseType(CaseType caseType) {
		caseTypeRepository.save(caseType);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCaseType(Long id) {
		caseTypeRepository.deleteById(id);
	}
}
