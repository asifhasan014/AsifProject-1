package com.softron.reporting.controller;

import com.softron.core.annotations.ApiController;
import com.softron.reporting.service.ReportingReportService;
import com.softron.reporting.to.ReportTO;

import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ReportingApiContants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController
public class ReportingReportController {

    @Autowired
    private ReportingReportService service;

    @GetMapping(value = OR_REPORT_ENDPOINT + ID_PATH_VAR)
    public ResponseEntity<ReportTO> getReport(@PathVariable(name = ID) String reportId) {
        return ResponseEntity.ok(service.get(reportId));
    }

    @GetMapping(value = OR_SUBMIT_ENDPOINT)
    public ResponseEntity<?> submit(@PathVariable(name = ID) String reportId) {
        service.submit(reportId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = OR_RECIPIENT_ENDPOINT)
    public ResponseEntity<?> getRecipients(@PathVariable(name = ID) String reportId) {
        return ResponseEntity.ok(service.getRecipients(reportId));
    }

    @GetMapping(value = OR_ACKS_ENDPOINT)
    public ResponseEntity<?> getAcks(@PathVariable(name = ID) String reportId) {
        return ResponseEntity.ok(service.getAcks(reportId));
    }

    @PostMapping(value = OR_REPORT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> save(@RequestBody ReportTO reportTO) {
        this.service.save(reportTO);
        return new ResponseEntity<>("{\"reportId\": \"" + reportTO.getReportId() + "\"}", HttpStatus.CREATED);
    }

}
