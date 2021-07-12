/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.CENSUS_DATA_ENTRY_PROJECT_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.BY_USER_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.census.schema.entity.CensusDataEntryProject;
import com.softron.census.schema.entity.CensusPeriod;
import com.softron.census.service.CensusDataEntryProjectService;
import com.softron.common.utils.AuthUtil;
import com.softron.core.annotations.ApiController;
import com.softron.core.domain.RequestContextData;

/**
 *
 * @author Mozahid
 */
@ApiController
public class CensusDataEntryProjectController {

    @Autowired
    private CensusDataEntryProjectService censusDataEntryProjectService;
    
    @Resource(name = "requestContextData")
	private RequestContextData requestContextData;

    @GetMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusDataEntryProject() {
        return ResponseEntity.ok(censusDataEntryProjectService.getAllCensusDataEntryProject());
    }
    
    @GetMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT + BY_USER_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusDataEntryProjectByUserOrg() {
    	Long moduleId = requestContextData.getModuleId().orElse(null);
    	return ResponseEntity.ok(censusDataEntryProjectService.getAllCensusDataEntryProjectByUserOrg(moduleId, AuthUtil.getUserName()));
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCensusDataEntryProject() {
        return ResponseEntity.ok(censusDataEntryProjectService.getAllActive());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusDataEntryProjectById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusDataEntryProjectService.getCensusDataEntryProjectById(id));
    }

    @DeleteMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCensusDataEntryProject(@PathVariable(ID) Long id) {
        try {
            censusDataEntryProjectService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCensusDataEntryProject(@RequestBody CensusDataEntryProject censusDataEntryProject) {
        CensusPeriod censusPeriod = new CensusPeriod();
        if (censusDataEntryProject.getCensusPeriod() != null) {
            censusPeriod.setId(censusDataEntryProject.getCensusPeriod().getId());
        }
        censusDataEntryProject.setActive(true);
        censusDataEntryProject.setCensusPeriod(censusPeriod);
        censusDataEntryProjectService.save(censusDataEntryProject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCensusDataEntryProject(@RequestBody CensusDataEntryProject censusDataEntryProject) {
        censusDataEntryProjectService.save(censusDataEntryProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = CENSUS_DATA_ENTRY_PROJECT_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusDataEntryProject(@RequestBody CensusDataEntryProject censusDataEntryProject) {
        return ResponseEntity.ok(censusDataEntryProjectService.getAllCensusDataEntryProjectFilter(censusDataEntryProject));
    }
}
