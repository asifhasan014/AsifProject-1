package com.softron.admin.controller;

import com.softron.admin.service.CaseTypeService;
import com.softron.core.annotations.ApiController;
import com.softron.core.enums.CaseFormType;
import com.softron.schema.admin.entity.CaseType;

import static com.softron.core.constants.AdminApiConstants.CASE_TYPE_ENDPOINT;
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
public class CaseTypeController {

    @Autowired
    private CaseTypeService service;

    @GetMapping(value = CASE_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCaseTypes() {
        return ResponseEntity.ok(service.getActiveCaseTypes());
    }

    @GetMapping(value = CASE_TYPE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCaseTypes() {
        return ResponseEntity.ok(service.getAllCaseTypes());
    }

    @GetMapping(value = CASE_TYPE_ENDPOINT + FORM + CHILD_ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCaseTypes(@PathVariable(CHILD_ID) CaseFormType caseType) {
        return ResponseEntity.ok(service.getActiveCaseTypes(caseType));
    }

    @GetMapping(value = CASE_TYPE_ENDPOINT + FORM + CHILD_ID_PATH_VAR + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCaseTypes(@PathVariable(CHILD_ID) CaseFormType caseType) {
        return ResponseEntity.ok(service.getAllCaseTypes(caseType));
    }

    @GetMapping(value = CASE_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCaseTypeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(service.getCaseTypeById(id));
    }

    @DeleteMapping(value = CASE_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCaseType(@PathVariable(ID) Long id) {
        service.deleteCaseType(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = CASE_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCaseType(@RequestBody CaseType caseType) {
        service.saveCaseType(caseType);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = CASE_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCaseType(@RequestBody CaseType caseType) {
        service.saveCaseType(caseType);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
