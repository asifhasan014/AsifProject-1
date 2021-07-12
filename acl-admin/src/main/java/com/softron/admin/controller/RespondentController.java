package com.softron.admin.controller;

import com.softron.admin.service.AdminService;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Respondent;

import static com.softron.core.constants.AdminApiConstants.RESPONDENT_ENDPOINT;
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
public class RespondentController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = RESPONDENT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveRespondents() {
        return ResponseEntity.ok(adminService.getActiveRespondents());
    }

    @GetMapping(value = RESPONDENT_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllRespondents() {
        return ResponseEntity.ok(adminService.getAllRespondents());
    }

    @GetMapping(value = RESPONDENT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getRespondentById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getRespondentById(id));
    }

    @DeleteMapping(value = RESPONDENT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteRespondent(@PathVariable(ID) Long id) {
        adminService.deleteRespondent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = RESPONDENT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveRespondent(@RequestBody Respondent resp) {
        adminService.saveRespondent(resp);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = RESPONDENT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateRespondent(@RequestBody Respondent resp) {
        adminService.saveRespondent(resp);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
