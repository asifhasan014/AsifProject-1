/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.repository;

import com.softron.schema.exam.entity.CandidateExamDetail;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mozahid
 */
@Repository
public interface CandidateExamDetailRepository extends JpaRepository<CandidateExamDetail, Long> {

    @Query(value = "SELECT MAX(E.NAME) AS EXAM_NAME, MAX(ES.NAME) AS SESSION_NAME, MAX(ES.SESSION_DATE) AS SESSION_DATE, COUNT(C.ID) AS CANDIDATE_COUNT, MAX(E.ID) AS EXAM_ID, MAX(ES.ID) AS SESSION_ID FROM CANDIDATE_EXAM_DETAIL  CE JOIN EXAM  E ON CE.EXAM_ID = E.ID JOIN EXAM_ORGANIZATION O ON O.ID = E.ORGANIZATION_ID JOIN CANDIDATE  C ON C.ID = CE.CANDIDATE_ID JOIN EXAM_SESSION  ES ON C.EXAMSESSION_ID = ES.ID WHERE O.ID = :ORGANIZATIONID OR E.ID = :EXAMID OR E.EXAM_YEAR = :EXAMYEAR GROUP BY ES.ID ", nativeQuery = true)
    public List<Object[]> getExamResult(@Param("ORGANIZATIONID") Long organizationId, @Param("EXAMID") Long examId, @Param("EXAMYEAR") Date examYear);

    @Query(value = "SELECT * FROM CANDIDATE_EXAM_DETAIL  c WHERE c.CANDIDATE_ID = :candidateId AND c.EXAM_ID= :examId", nativeQuery = true)
    public List<CandidateExamDetail> findByCandidateIdAndExamId(@Param("candidateId") Long candidateId, @Param("examId") Long examId);

    @Query(value = "SELECT C.ROLL_NO AS ROLL_NO, C.NAME AS CANDIDATE_NAME,CED.KEYBOARD AS KEYBOARD, CED.ERRORS AS ERRORS, CED.WORDS_PER_MINUTE AS WORD_PER_MIN, CED.WORD_COUNT AS WORD_COUNT, CED.PASS AS RESULT FROM CANDIDATE_EXAM_DETAIL CED JOIN CANDIDATE C ON CED.CANDIDATE_ID = C.ID WHERE CED.EXAM_ID = :EXAMID AND C.EXAMSESSION_ID = :EXAMSESSIONID", nativeQuery = true)
    public List<Object[]> findByExamIdAndSession(@Param("EXAMID") Long examId, @Param("EXAMSESSIONID") Long examSessionId);
}
