package com.softron.admin.controller;

import com.softron.admin.service.StatusService;
import com.softron.core.annotations.ApiController;
import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.Status;

import static com.softron.core.constants.AdminApiConstants.STATUS_ENDPOINT;
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
public class StatusController {

    @Autowired
    private StatusService service;

    @GetMapping(value = STATUS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveStatus() {
        return ResponseEntity.ok(service.getActiveStatus());
    }

    @GetMapping(value = STATUS_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllStatus() {
        return ResponseEntity.ok(service.getAllStatus());
    }

    @GetMapping(value = STATUS_ENDPOINT + FORM + CHILD_ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveStatus(@PathVariable(CHILD_ID) CaseFormType caseType) {
        return ResponseEntity.ok(service.getActiveStatus(caseType));
    }

    @GetMapping(value = STATUS_ENDPOINT + FORM + CHILD_ID_PATH_VAR + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllStatus(@PathVariable(CHILD_ID) CaseFormType caseType) {
        return ResponseEntity.ok(service.getAllStatus(caseType));
    }

    @GetMapping(value = STATUS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getStatusById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(service.getStatusById(id));
    }

    @DeleteMapping(value = STATUS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteStatus(@PathVariable(ID) Long id) {
        service.deleteStatus(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = STATUS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveStatus(@RequestBody Status status) {
        service.saveStatus(status);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = STATUS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateStatus(@RequestBody Status status) {
        service.saveStatus(status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
