/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.Post;

/**
 *
 * @author Mozahid
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    @Query("Select p from Post p where p.active=true")
    public List<Post> findAllActive();

    @Query("Select p from Post p")
    public List<Post> findAllPost();

    @Query("Select p from Post p where p.name=:name")
    public List<Post> findDuplicatePost(@Param("name") String name);

    @Query("Select p from Post p where p.postClass.id=:id")
    public List<Post> getPostByClassId(@Param("id") Long id);

}
