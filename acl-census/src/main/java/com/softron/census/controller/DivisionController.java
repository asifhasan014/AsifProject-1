/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.DIVISION_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
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

import com.softron.census.schema.entity.Division;
import com.softron.census.service.DivisionService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class DivisionController {

    @Autowired
    private DivisionService divisionService;

    @GetMapping(value = DIVISION_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getDivision() {
        return ResponseEntity.ok(divisionService.getAllDistrict());
    }

    @GetMapping(value = DIVISION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveDivision() {
        return ResponseEntity.ok(divisionService.getAllActive());
    }

    @GetMapping(value = DIVISION_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getDivisionById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(divisionService.getDivisionById(id));
    }

    @DeleteMapping(value = DIVISION_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteDivision(@PathVariable(ID) Long id) {
        try {
            divisionService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DIVISION_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveDivision(@RequestBody Division division, @PathVariable(FLAG_EDIT) String flag) {
        List<Division> divisions = divisionService.findDuplicateDivision(division.getName());
        if (!divisions.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (divisions.contains(division.getName())) {
                    divisionService.save(division);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        } else {
            divisionService.save(division);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = DIVISION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateDivision(@RequestBody Division division) {
        divisionService.save(division);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
