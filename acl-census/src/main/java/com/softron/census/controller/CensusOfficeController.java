/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.CENSUS_OFFICE_ENDPOINT;
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

import com.softron.census.schema.entity.CensusOffice;
import com.softron.census.service.CensusOfficeService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class CensusOfficeController {

    @Autowired
    private CensusOfficeService censusOfficeService;

    @GetMapping(value = CENSUS_OFFICE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusOffice() {
        return ResponseEntity.ok(censusOfficeService.getAllCensusOffice());
    }

    @GetMapping(value = CENSUS_OFFICE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCensusOffice() {
        return ResponseEntity.ok(censusOfficeService.getAllActive());
    }

    @GetMapping(value = CENSUS_OFFICE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusOfficeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusOfficeService.getCensusOfficeById(id));
    }

    @DeleteMapping(value = CENSUS_OFFICE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCensusOffice(@PathVariable(ID) Long id) {
        try {
            censusOfficeService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = CENSUS_OFFICE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCensusOffice(@RequestBody CensusOffice censusOffice) {
        censusOfficeService.save(censusOffice);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = CENSUS_OFFICE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCensusOffice(@RequestBody CensusOffice censusOffice) {
        censusOfficeService.save(censusOffice);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
