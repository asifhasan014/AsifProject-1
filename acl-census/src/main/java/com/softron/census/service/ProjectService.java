/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.Project;
import com.softron.census.schema.filter.ProjectFilter;
import com.softron.census.schema.repository.ProjectRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**author
 *
 * @author Mozahid
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public List<Project> getAllActive() {
        List<Project> projects = projectRepository.findAllActive();
        return projects;
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProject() {
        List<Project> projects = projectRepository.findAllProject();
        return projects;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        projectRepository.deleteById(id);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Project> findDuplicateProject(Project project) {
       return projectRepository.findDuplicateProject(project.getName(),project.getCensusOrganization().getId());
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Project doesn't exist for id=" + id));
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjectFilter(String name) {
        Project project = new Project();
        project.setName(name);
        Specification<Project> specification = new ProjectFilter(project);
        List<Project> result = projectRepository.findAll(specification);
        return result;
    }
}
