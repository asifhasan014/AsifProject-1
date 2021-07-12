package com.softron.masterdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.SubSection;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.SubSectionRepository;

@Service
public class SubSectionService {
	@Autowired
	SubSectionRepository subSectionRepository;

	public SubSection save(SubSection subSection) {

		return subSectionRepository.save(subSection);
	}

	public List<SubSection> findAll() {
		return subSectionRepository.findAll();
	}

	public SubSection getSubSectionById(Long id) {
		return subSectionRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Doesn't exist for id=" + id));

	}

	public void delete(Long id) {
		subSectionRepository.deleteById(id);
	}

	public SubSection update(Long id, SubSection subSectionDetails) {
		SubSection subSection = getSubSectionById(id);
		if (subSection == null) {
			return null;
		}
		subSection.setName(subSectionDetails.getName());
		subSection.setCapacity(subSectionDetails.getCapacity());
		subSection.setNumberOfLabor(subSectionDetails.getNumberOfLabor());
		subSection.setNumberOfMachine(subSectionDetails.getNumberOfMachine());
		subSection.setSubSectionHead(subSectionDetails.getSubSectionHead());
	
		SubSection updateSubSection = save(subSection);
		return updateSubSection;

	}
}
