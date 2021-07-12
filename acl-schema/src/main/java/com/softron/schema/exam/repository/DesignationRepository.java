/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.repository;
import com.softron.schema.exam.entity.Designation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mozahid
 */
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    @Query("Select a from Designation a")
    public List<Designation> findAllActive();
}
