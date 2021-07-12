package com.softron.schema.exam.repository;

import java.util.List;

import com.softron.schema.exam.entity.Exam;

public interface ExamRepositoryCustom {

	public List<Exam> findUserOrganizationExams(Long moduleId, String userId);

}
