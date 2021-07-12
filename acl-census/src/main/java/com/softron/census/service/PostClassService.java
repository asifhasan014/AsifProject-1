/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.PostClass;
import com.softron.census.schema.repository.PostClassRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class PostClassService {

    @Autowired
    private PostClassRepository postClassRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(PostClass postClass) {
        postClassRepository.save(postClass);
    }

    @Transactional(readOnly = true)
    public List<PostClass> getAllActive() {
        List<PostClass> postClasses = postClassRepository.findAllActive();
        return postClasses;
    }

    @Transactional(readOnly = true)
    public List<PostClass> getAllPostClass() {
        List<PostClass> postClasses = postClassRepository.findAllPostClass();
        return postClasses;
    }

    @Transactional(readOnly = true)
    public List<PostClass> findDuplicateClass(String name) {
        List<PostClass> postClasses = postClassRepository.findDuplicateClass(name);
        return postClasses;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        postClassRepository.deleteById(id);
    }

    public PostClass getPostClassById(Long id) {
        return postClassRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Post Class doesn't exist for id=" + id));
    }
}
