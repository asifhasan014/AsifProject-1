package com.softron.admin.controller;

import com.softron.admin.service.CourtService;
import com.softron.core.annotations.ApiController;
import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.Court;

import static com.softron.core.constants.AdminApiConstants.COURT_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.CHILD_ID;
import static com.softron.core.constants.ApiConstants.CHILD_ID_PATH_VAR;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.FORM;
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

@ApiController
public class CourtController {

    @Autowired
    private CourtService service;

    @GetMapping(value = COURT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCourts() {
        return ResponseEntity.ok(service.getActiveCourts());
    }

    @GetMapping(value = COURT_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCourts() {
        return ResponseEntity.ok(service.getAllCourts());
    }
    
    @GetMapping(value = COURT_ENDPOINT + FORM + CHILD_ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCourts(@PathVariable(CHILD_ID) CaseFormType caseType) {
        return ResponseEntity.ok(service.getActiveCourts(caseType));
    }

    @GetMapping(value = COURT_ENDPOINT + FORM + CHILD_ID_PATH_VAR + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCourts(@PathVariable(CHILD_ID) CaseFormType caseType) {
        return ResponseEntity.ok(service.getAllCourts(caseType));
    }

    @GetMapping(value = COURT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCourtById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(service.getCourtById(id));
    }

    @DeleteMapping(value = COURT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCourt(@PathVariable(ID) Long id) {
        service.deleteCourt(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = COURT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCourt(@RequestBody Court court) {
        service.saveCourt(court);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = COURT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCourt(@RequestBody Court court) {
        service.saveCourt(court);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
