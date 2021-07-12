/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.PROJECT_TYPE_ENDPOINT;
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

import com.softron.census.schema.entity.ProjectType;
import com.softron.census.service.ProjectTypeService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class ProjectTypeController {

    @Autowired
    private ProjectTypeService projectTypeService;

    @GetMapping(value = PROJECT_TYPE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getProjectType() {
        return ResponseEntity.ok(projectTypeService.getAllProjectType());
    }

    @GetMapping(value = PROJECT_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveProjectType() {
        return ResponseEntity.ok(projectTypeService.getAllActive());
    }

    @GetMapping(value = PROJECT_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getProjectTypeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(projectTypeService.getProjectTypeById(id));
    }

    @DeleteMapping(value = PROJECT_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteProjectType(@PathVariable(ID) Long id) {
        try {
            projectTypeService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = PROJECT_TYPE_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveProjectType(@RequestBody ProjectType projectType, @PathVariable(FLAG_EDIT) String flag) {
        List<ProjectType> projectTypes = projectTypeService.findDuplicateProjectType(projectType.getName());

        if (!projectTypes.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (projectTypes.contains(projectType.getName())) {
                    projectTypeService.save(projectType);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        } else {
            projectTypeService.save(projectType);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = PROJECT_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateProjectType(@RequestBody ProjectType projectType) {
        projectTypeService.save(projectType);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
