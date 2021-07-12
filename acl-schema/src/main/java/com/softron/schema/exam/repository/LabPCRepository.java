/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softron.schema.exam.entity.LabPC;
/**
 *
 * @author Mozahid
 */
@Repository
public interface LabPCRepository extends JpaRepository<LabPC, Long> {

//    @Modifying
//    @Transactional
//    @Query("delete from LabPC u where u.firstName = ?1")
//    void deleteUsersByFirstName(String firstName);
    
    
}
