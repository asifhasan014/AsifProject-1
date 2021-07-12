package com.softron.schema.exam.repository;

import java.util.List;

import com.softron.schema.exam.entity.Lab;

public interface LabRepositoryCustom {
	
	public List<Lab> findUserOrganizationLabs(Long moduleId, String userId);

}
