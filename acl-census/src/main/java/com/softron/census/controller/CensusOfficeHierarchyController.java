/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.CENSUS_OFFICE_HIERARCHY_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.census.schema.entity.CensusOfficeHierarchy;
import com.softron.census.service.CensusOfficeHierarchyService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class CensusOfficeHierarchyController {

    @Autowired
    private CensusOfficeHierarchyService censusOfficeHierarchyService;

    @GetMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusOfficeHierarchies() {
        return ResponseEntity.ok(censusOfficeHierarchyService.getAllCensusOfficeHierarchy());
    }

    @GetMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCensusOfficeHierarchies() {
        return ResponseEntity.ok(censusOfficeHierarchyService.getAllActive());
    }

    @GetMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusOfficeHierarchyById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusOfficeHierarchyService.getCensusOfficeHierarchyById(id));
    }

    @DeleteMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCensusOfficeHierarchy(@PathVariable(ID) Long id) {
        try {
            censusOfficeHierarchyService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCensusOfficeHierarchy(@RequestBody CensusOfficeHierarchy censusOfficeHierarchy) {
        censusOfficeHierarchyService.save(censusOfficeHierarchy);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCensusOfficeHierarchy(@RequestBody CensusOfficeHierarchy censusOfficeHierarchy) {
        censusOfficeHierarchyService.save(censusOfficeHierarchy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = CENSUS_OFFICE_HIERARCHY_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficesSearch(@RequestBody CensusOfficeHierarchy censusOfficeHierarchy) {
        return ResponseEntity.ok(censusOfficeHierarchyService.getAllCensusOfficeHierarchyFilter(censusOfficeHierarchy.getCensusOffice()));
    }
}
