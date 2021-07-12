package com.softron.reporting.controller;

import com.softron.core.annotations.ApiController;
import com.softron.reporting.entity.ReportType;
import com.softron.reporting.service.ReportingReportTypeService;

import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ReportingApiContants.OR_TYPES_ENDPOINT;

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
public class ReportingTypeController {

    @Autowired
    private ReportingReportTypeService reportTypeService;

    @GetMapping(value = OR_TYPES_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveReportTypes() {
        return ResponseEntity.ok(reportTypeService.getActiveReportTypes());
    }

    @GetMapping(value = OR_TYPES_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllReportTypes() {
        return ResponseEntity.ok(reportTypeService.getAllReportTypes());
    }

    @GetMapping(value = OR_TYPES_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrgTypeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(reportTypeService.getReportTypeById(id));
    }

    @DeleteMapping(value = OR_TYPES_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteReportType(@PathVariable(ID) Long id) {
        reportTypeService.deleteReportType(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = OR_TYPES_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveReportType(@RequestBody ReportType reportType) {
        reportTypeService.saveReportType(reportType);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = OR_TYPES_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateOrgType(@RequestBody ReportType reportType) {
        reportTypeService.saveReportType(reportType);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}