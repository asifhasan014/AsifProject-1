package com.softron.admin.controller;

import static com.softron.core.constants.AdminApiConstants.MODULE_ENDPOINT;
import static com.softron.core.constants.AdminApiConstants.MODULE_ORG_USER_MAPPING_ENDPOINT;
import static com.softron.core.constants.AdminApiConstants.MODULE_USERS_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.CHILD_ID;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.admin.service.ModuleService;
import com.softron.admin.transferobjects.ModuleTO;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Module;
import com.softron.schema.bo.UserBO;

@ApiController
public class ModuleController {

	@Autowired
    private ModuleService moduleService;

    @GetMapping(value = MODULE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public List<Module> getActiveModules() {
        return moduleService.getAllActiveModules();
    }

    @GetMapping(value = MODULE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping(value = MODULE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public Module getModule(@PathVariable(ID) Long id) {
        return moduleService.getModuleById(id);
    }

    @GetMapping(value = MODULE_USERS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public Map<String, List<UserBO>> getModuleUsers(@PathVariable(ID) Long moduleId, @PathVariable(CHILD_ID) Long orgId) {
        return moduleService.getModuleUsers(moduleId, orgId);
    }

    @PostMapping(value = MODULE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<Void> saveModule(@RequestBody ModuleTO moduleTO) {
        moduleService.save(moduleTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = MODULE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<Void> deleteModule(@PathVariable(ID) Long id) {
        moduleService.deleteModuleById(id);
        return ResponseEntity.ok().build();
    }   


    @PostMapping(value = MODULE_ORG_USER_MAPPING_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOrgUsersMapping(@PathVariable(ID) Long moduleId, @PathVariable(CHILD_ID) Long orgId, @RequestBody List<String> userIds) {
    	moduleService.saveOrgUserMapping(moduleId, orgId, userIds);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
