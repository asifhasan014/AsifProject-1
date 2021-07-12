package com.softron.admin.controller;

import static com.softron.core.constants.AdminApiConstants.ROLES_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.BY_MODULE_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.admin.service.AdminService;
import com.softron.admin.transferobjects.RoleTO;
import com.softron.core.annotations.ApiController;

@ApiController
public class UserRoleController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = ROLES_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveRoles() {
        return ResponseEntity.ok(adminService.getActiveRoles());
    }

    @GetMapping(value = ROLES_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(adminService.getAllRoles());
    }

    @GetMapping(value = ROLES_ENDPOINT + ID_PATH_VAR + BY_MODULE_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getRolesByModule(@PathVariable(ID) List<Long> moduleIds) {
        return ResponseEntity.ok(adminService.getRolesByModuleIds(moduleIds));
    }

    @GetMapping(value = ROLES_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getRoleById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getRoleById(id));
    }

    @DeleteMapping(value = ROLES_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteRole(@PathVariable(ID) Long id) {
        adminService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = ROLES_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveRole(@Valid @RequestBody RoleTO roleTO) {        
        adminService.saveRole(roleTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = ROLES_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleTO roleTO) {
        adminService.saveRole(roleTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
