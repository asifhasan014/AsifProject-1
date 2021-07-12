package com.softron.reporting.controller;

import com.softron.core.annotations.ApiController;
import com.softron.reporting.entity.ReportOrgType;
import com.softron.reporting.service.ReportOrgTypeService;

import static com.softron.core.constants.ReportingApiContants.OR_ORG_TYPE_ENDPOINT;
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

@ApiController
public class ReportingOrgTypeController {

    @Autowired
    private ReportOrgTypeService service;

    @GetMapping(value = OR_ORG_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveOrgTypes() {
        return ResponseEntity.ok(service.getActiveOrgTypes());
    }

    @GetMapping(value = OR_ORG_TYPE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOrgTypes() {
        return ResponseEntity.ok(service.getAllOrgTypes());
    }

    @GetMapping(value = OR_ORG_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrgTypeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(service.getOrgTypeById(id));
    }

    @DeleteMapping(value = OR_ORG_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteOrgType(@PathVariable(ID) Long id) {
        service.deleteOrgType(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = OR_ORG_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOrgType(@RequestBody ReportOrgType org) {
        service.saveOrgType(org);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = OR_ORG_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateOrgType(@RequestBody ReportOrgType org) {
        service.saveOrgType(org);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
