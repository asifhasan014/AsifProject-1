package com.softron.schema.exam.repository;

import java.util.List;

import com.softron.schema.admin.entity.ExamOrganization;

public interface ExamOrganizationRepositoryCustom {

	public List<ExamOrganization> findByUserId(Long moduleId, String userId);

}
