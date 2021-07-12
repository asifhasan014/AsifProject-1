/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.exam.entity.Exam;
import com.softron.schema.exam.entity.ExamSession;

/**
 *
 * @author Mozahid
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long>, ExamRepositoryCustom {

	@Query("Select a from Exam a")
	public List<Exam> findAllActive();

	@Query("Select a from ExamSession a")
	public List<ExamSession> getExamSessionList();

	@Query("Select a from ExamSession a where a.exam.id=:examId")
	public List<ExamSession> getExamSessionListByExamId(@Param("examId") Long examId);

	@Query("Select e from Exam e where e.name= :name and e.organization.id= :orgId")
	public List<Exam> findDuplicateExam(@Param("name") String name, @Param("orgId") Long orgId);
}
