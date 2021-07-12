package com.softron.admin.controller;

import com.softron.admin.service.AdminService;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Advocate;

import static com.softron.core.constants.AdminApiConstants.ADVOCATE_ENDPOINT;
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
public class AdvocateController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = ADVOCATE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveAdvocates() {
        return ResponseEntity.ok(adminService.getActiveAdvocates());
    }

    @GetMapping(value = ADVOCATE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllAdvocates() {
        return ResponseEntity.ok(adminService.getAllAdvocates());
    }

    @GetMapping(value = ADVOCATE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAdvocateById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getAdvocateById(id));
    }

    @DeleteMapping(value = ADVOCATE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteAdvocate(@PathVariable(ID) Long id) {
        adminService.deleteAdvocateById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = ADVOCATE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveAdvocate(@RequestBody Advocate advocate) {
        adminService.saveAdvocate(advocate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = ADVOCATE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateAdvocate(@RequestBody Advocate advocate) {
        adminService.saveAdvocate(advocate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
