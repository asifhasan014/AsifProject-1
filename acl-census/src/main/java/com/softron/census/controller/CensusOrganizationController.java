/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.ALL_MINISTRY;
import static com.softron.census.constant.WebApiConstants.ALL_ORG_BY_PARENT_ID;
import static com.softron.census.constant.WebApiConstants.CENSUS_DATA_ENTRY_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.CENSUS_ORGANIZATION_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.GET_ORG_TYPE_BY_ID;
import static com.softron.census.constant.WebApiConstants.OFFICE_TYPE_BY_ORG_TYPE;
import static com.softron.census.constant.WebApiConstants.OFFICE_TYPE_ENUM;
import static com.softron.census.constant.WebApiConstants.ORG_TYPE_ENUM;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.BY_USER_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ApiConstants.ORG_TYPE;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.census.schema.entity.CensusOrganization;
import com.softron.census.service.CensusOrganizationService;
import com.softron.common.utils.AuthUtil;
import com.softron.core.annotations.ApiController;
import com.softron.core.domain.RequestContextData;

/**
 *
 * @author Mozahid
 */
@ApiController
public class CensusOrganizationController {

    @Autowired
    private CensusOrganizationService censusOrganizationService;
    
    @Resource(name = "requestContextData")
	private RequestContextData requestContextData;

    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusOrganization() {
        return ResponseEntity.ok(censusOrganizationService.getAllCensusOrganization());
    }

    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCensusOrganization() {
        return ResponseEntity.ok(censusOrganizationService.getAllActive());
    }
    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT + ALL_MINISTRY + BY_USER_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getUserCensusOrganizationByMinistry() {
    	Long moduleId = requestContextData.getModuleId().orElse(null);
        return ResponseEntity.ok(censusOrganizationService.getUserOrganizationByMinistry(moduleId, AuthUtil.getUserName()));
    }

    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT + OFFICE_TYPE_ENUM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficeType() {
        return ResponseEntity.ok(censusOrganizationService.getAllOfficeType());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + ORG_TYPE_ENUM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOrgType() {
        return ResponseEntity.ok(censusOrganizationService.getAllOrgType());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + OFFICE_TYPE_BY_ORG_TYPE + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficeTypeByOrgType(@PathVariable(ID) String name) {
        return ResponseEntity.ok(censusOrganizationService.getAllOfficeTypeByOrgType(name));
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + GET_ORG_TYPE_BY_ID + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrgTypeById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusOrganizationService.getOrgTypeById(id));
    }

    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusOrganizationById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusOrganizationService.getCensusOrganizationById(id));
    }

    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT + ALL_MINISTRY, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllMinistry() {
        return ResponseEntity.ok(censusOrganizationService.findAllByMinistry());
    }

    @DeleteMapping(value = CENSUS_ORGANIZATION_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCensusOrganization(@PathVariable(ID) Long id) {
        try {
            censusOrganizationService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = CENSUS_ORGANIZATION_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCensusOrganization(@RequestBody CensusOrganization censusOrganization, @PathVariable(FLAG_EDIT) String flag) {
            List<CensusOrganization> censusOrganizations = censusOrganizationService.findDuplicateCensusOrganization(censusOrganization.getNameEng(), censusOrganization.getOrgType().name(),censusOrganization.getParentId()== null ? 0:censusOrganization.getParentId()); 
        if (!censusOrganizations.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (!censusOrganizations.contains(censusOrganization.getNameEng()) && !censusOrganizations.contains(censusOrganization.getParentId()) && !censusOrganizations.contains(censusOrganization.getParentId()== null ? 0:censusOrganization.getParentId())) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    censusOrganizationService.save(censusOrganization);
                }
            }
        } else {
            censusOrganizationService.save(censusOrganization);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = CENSUS_ORGANIZATION_ENDPOINT + ALL_ORG_BY_PARENT_ID, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusOrganizationByOrgTypeAndParentId(@PathVariable(ID) String id, @PathVariable(ORG_TYPE) String orgType) {
        return ResponseEntity.ok(censusOrganizationService.findByOrgTypeAndparentId(id, orgType));
    }

    @PutMapping(value = CENSUS_ORGANIZATION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCensusOrganization(@RequestBody CensusOrganization censusOrganization) {
        censusOrganizationService.save(censusOrganization);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
