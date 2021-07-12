/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.DISTRICT_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.DIVISION;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.census.schema.entity.District;
import com.softron.census.schema.entity.Division;
import com.softron.census.service.DistrictService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping(value = DISTRICT_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getDistrict() {
        return ResponseEntity.ok(districtService.getAllDistrict());
    }

    @GetMapping(value = DISTRICT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveDistrict() {
        return ResponseEntity.ok(districtService.getAllActive());
    }

    @GetMapping(value = DISTRICT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getDistrictById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(districtService.getDistrictById(id));
    }

    @PostMapping(value = DISTRICT_ENDPOINT + DIVISION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getDistrictByDivision(@RequestBody Division division) {
        return ResponseEntity.ok(districtService.getDistrictByDivision(division));
    }

    @DeleteMapping(value = DISTRICT_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteDistrict(@PathVariable(ID) Long id) {
        try {
            districtService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DISTRICT_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveDistrict(@RequestBody District district, @PathVariable(FLAG_EDIT) String flag) {
        List<District> districts = districtService.findDuplicateDistrict(district.getName());
        if (!districts.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (districts.contains(district.getName())) {
                    districtService.save(district);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        } else {
            districtService.save(district);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = DISTRICT_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateDistrict(@RequestBody District district) {
        districtService.save(district);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
