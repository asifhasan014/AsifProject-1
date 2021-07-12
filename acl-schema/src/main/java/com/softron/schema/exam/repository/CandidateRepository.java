/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.repository;

import com.softron.schema.exam.entity.Candidate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mozahid
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = "SELECT E.ID AS EXAM_ID, C.ID AS CANDIDATE_ID, ES.ID AS SESSION_ID FROM EXAM "
            + "E JOIN EXAM_SESSION  ES ON E.ID = ES.EXAM_ID JOIN CANDIDATE  C  "
            + "ON C.EXAMSESSION_ID = ES.ID JOIN LAB_PC  L  "
            + "ON L.LAB_ID = ES.LAB_ID WHERE C.ROLL_NO =:ROLLNO AND L.IP_ADDRESS =:IPADDRESS AND E.ID =:EXAMID AND E.DESIGNATION_ID =:DESIGNATIONID ", nativeQuery = true)
    public List<Object[]> login(@Param("EXAMID") Long examId, @Param("DESIGNATIONID") Long designationId, @Param("ROLLNO") Long rollNo, @Param("IPADDRESS") String ipAddress);

    public Optional<Candidate> findByRollNo(String rollNo);

}
