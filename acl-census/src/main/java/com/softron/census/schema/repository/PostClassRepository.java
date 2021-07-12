/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.PostClass;

/**
 *
 * @author Mozahid
 */
@Repository
public interface PostClassRepository extends JpaRepository<PostClass, Long> {

    @Query("Select p from PostClass p where p.active=true order by displayOrder ASC")
    public List<PostClass> findAllActive();

    @Query("Select p from PostClass p  order by displayOrder ASC")
    public List<PostClass> findAllPostClass();

    @Query("Select p from PostClass p where p.name=:name ")
    public List<PostClass> findDuplicateClass(@Param("name") String name);

}
