/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.PROJECT_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.census.schema.entity.Project;
import com.softron.census.service.ProjectService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = PROJECT_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProject());
    }

    @GetMapping(value = PROJECT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveProjects() {
        return ResponseEntity.ok(projectService.getAllActive());
    }

    @GetMapping(value = PROJECT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getProjectById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @DeleteMapping(value = PROJECT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteProject(@PathVariable(ID) Long id) {

        try {
            projectService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = PROJECT_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveProject(@RequestBody Project project, @PathVariable(FLAG_EDIT) String flag) {
        List<Project> posts = projectService.findDuplicateProject(project);
     
        if (!posts.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                 if (!posts.contains(project.getName()) && !posts.contains(project.getCensusOrganization())) {
                     
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    projectService.save(project);
                }
            }
        } else {
            projectService.save(project);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = PROJECT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        projectService.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = PROJECT_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllprojectFilter(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.getAllProjectFilter(project.getName()));
    }

}
