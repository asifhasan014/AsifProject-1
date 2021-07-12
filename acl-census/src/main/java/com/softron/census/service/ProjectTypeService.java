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

import com.softron.census.schema.entity.ProjectType;
import com.softron.census.schema.repository.ProjectTypeRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class ProjectTypeService {

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ProjectType projectType) {
        projectTypeRepository.save(projectType);
    }

    @Transactional(readOnly = true)
    public List<ProjectType> getAllActive() {
        List<ProjectType> districts = projectTypeRepository.findAllActive();
        return districts;
    }

    @Transactional(readOnly = true)
    public List<ProjectType> getAllProjectType() {
        List<ProjectType> projectTypes = projectTypeRepository.findAll();
        return projectTypes;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        projectTypeRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProjectType> findDuplicateProjectType(final String name) {
        return projectTypeRepository.findDuplicateProjectType(name);
    }

    public ProjectType getProjectTypeById(Long id) {
        return projectTypeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("ProjectType doesn't exist for id=" + id));
    }
}
