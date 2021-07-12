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

import com.softron.census.schema.entity.Grade;
import com.softron.census.schema.repository.GradeRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Grade grade) {
        gradeRepository.save(grade);
    }

    @Transactional(readOnly = true)
    public List<Grade> getAllActive() {
        List<Grade> grades = gradeRepository.findAllActive();
        return grades;
    }

    @Transactional(readOnly = true)
    public List<Grade> getAllGrade() {
        List<Grade> grades = gradeRepository.findAllGrade();
        return grades;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        gradeRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Grade> findDuplicateGrade(String name) {
        List<Grade> grades = gradeRepository.findDuplicateGrade(name);
        return grades;
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Grade doesn't exist for id=" + id));
    }
}
