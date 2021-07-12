/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.census.constant.WebApiConstants.OFFICE_SEARCH_ENDPOINT;
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

import com.softron.census.schema.entity.CensusOfficeSearch;
import com.softron.census.service.CensusOfficeSearchService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class CensusOfficeSearchController {

    @Autowired
    private CensusOfficeSearchService censusOfficeSearchService;

    @GetMapping(value = OFFICE_SEARCH_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficeSearches() {
        return ResponseEntity.ok(censusOfficeSearchService.getAllCensusOfficeSearch());
    }

    @GetMapping(value = OFFICE_SEARCH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveOfficeSearches() {
        return ResponseEntity.ok(censusOfficeSearchService.getAllActive());
    }

    @GetMapping(value = OFFICE_SEARCH_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOfficeSearchById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusOfficeSearchService.getOfficeSearchById(id));
    }

    @DeleteMapping(value = OFFICE_SEARCH_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteOfficeSearch(@PathVariable(ID) Long id) {
        try {
            censusOfficeSearchService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = OFFICE_SEARCH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOfficeSearch(@RequestBody CensusOfficeSearch officeSearch) {
        censusOfficeSearchService.save(officeSearch);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = OFFICE_SEARCH_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficesSearchFilter(@RequestBody CensusOfficeSearch censusOfficeSearch) {
        return ResponseEntity.ok(censusOfficeSearchService.getAllOfficeSearch(censusOfficeSearch.getOfficeName()));

    }

    @PutMapping(value = OFFICE_SEARCH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateOfficeSearch(@RequestBody CensusOfficeSearch officeSearch) {
        censusOfficeSearchService.save(officeSearch);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
