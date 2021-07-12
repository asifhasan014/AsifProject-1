package com.softron.admin.controller;

import com.softron.admin.service.AdminService;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.OrgType;

import static com.softron.core.constants.AdminApiConstants.ORG_TYPE_ENDPOINT;
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
public class OrgTypeController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = ORG_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveOrgTypes() {
        return ResponseEntity.ok(adminService.getActiveOrgTypes());
    }

    @GetMapping(value = ORG_TYPE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOrgTypes() {
        return ResponseEntity.ok(adminService.getAllOrgTypes());
    }

    @GetMapping(value = ORG_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrgTypeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getOrgTypeById(id));
    }

    @DeleteMapping(value = ORG_TYPE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteOrgType(@PathVariable(ID) Long id) {
        adminService.deleteOrgType(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = ORG_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOrgType(@RequestBody OrgType org) {
        adminService.saveOrgType(org);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = ORG_TYPE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateOrgType(@RequestBody OrgType org) {
        adminService.saveOrgType(org);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
