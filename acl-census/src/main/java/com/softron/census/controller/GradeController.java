/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.GRADE_ENDPOINT;
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

import com.softron.census.schema.entity.Grade;
import com.softron.census.service.GradeService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping(value = GRADE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getGrade() {
        return ResponseEntity.ok(gradeService.getAllGrade());
    }

    @GetMapping(value = GRADE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveGrade() {
        return ResponseEntity.ok(gradeService.getAllActive());
    }

    @GetMapping(value = GRADE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getGradeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(gradeService.getGradeById(id));
    }

    @DeleteMapping(value = GRADE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteGrade(@PathVariable(ID) Long id) {
        try {
            gradeService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = GRADE_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveGrade(@RequestBody Grade grade, @PathVariable(FLAG_EDIT) String flag) {

        List<Grade> grades = gradeService.findDuplicateGrade(grade.getName());
        if (!grades.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                gradeService.save(grade);
            }
        } else {
            gradeService.save(grade);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(value = GRADE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateGrade(@RequestBody Grade grade) {
        gradeService.save(grade);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
