package com.softron.admin.controller;

import com.softron.admin.service.AdminService;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Petitioner;

import static com.softron.core.constants.AdminApiConstants.PETITIONER_ENDPOINT;
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
public class PetitionerController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = PETITIONER_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActivePetitioners() {
        return ResponseEntity.ok(adminService.getActivePetitioners());
    }

    @GetMapping(value = PETITIONER_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPetitioners() {
        return ResponseEntity.ok(adminService.getAllPetitioners());
    }

    @GetMapping(value = PETITIONER_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getPetitionerById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getPetitionerById(id));
    }

    @DeleteMapping(value = PETITIONER_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deletePetitioner(@PathVariable(ID) Long id) {
        adminService.deletePetitioner(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = PETITIONER_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> savePetitioner(@RequestBody Petitioner pet) {
        adminService.savePetitioner(pet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = PETITIONER_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updatePetitioner(@RequestBody Petitioner pet) {
        adminService.savePetitioner(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
