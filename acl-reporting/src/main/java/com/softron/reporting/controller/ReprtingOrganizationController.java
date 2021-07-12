package com.softron.reporting.controller;

import com.softron.common.transferobjects.MapTO;
import com.softron.common.transferobjects.RawTree;
import com.softron.common.transferobjects.TreeTO;
import com.softron.common.utils.AuthUtil;
import com.softron.common.utils.TreeBuilder;
import com.softron.core.annotations.ApiController;
import com.softron.reporting.entity.ReportOrganization;
import com.softron.reporting.service.ReportingOrganizationService;

import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ApiConstants.MY_VERB;
import static com.softron.core.constants.ApiConstants.TREE_VERB;
import static com.softron.core.constants.ReportingApiContants.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
public class ReprtingOrganizationController {

    @Autowired
    private ReportingOrganizationService orgService;

    @GetMapping(value = OR_ORGS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveOrganizations() {
        return ResponseEntity.ok(orgService.getActiveOrganizations());
    }

    @GetMapping(value = OR_ORGS_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOrganizations() {
        return ResponseEntity.ok(orgService.getAllOrganizations());
    }

    @GetMapping(value = OR_ORGS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrganizationById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(orgService.getOrganizationById(id));
    }

    @DeleteMapping(value = OR_ORGS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteOrganization(@PathVariable(ID) Long id) {
        orgService.deleteOrganization(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = OR_ORGS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOrganization(@RequestBody ReportOrganization org) {
        orgService.saveOrganization(org);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = OR_ORGS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateOrganization(@RequestBody ReportOrganization org) {
        orgService.saveOrganization(org);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = OR_ORGS_ENDPOINT + TREE_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public List<TreeTO> getLocationTree() {
        List<ReportOrganization> locations = orgService.getActiveOrganizations();
        List<RawTree> rawData = locations.stream().map(l -> new RawTree(l.getId(), l.getNameEng(), l.getParentId())).collect(toList());
        return TreeBuilder.build(rawData);
    }

    @GetMapping(value = OR_ORGS_ENDPOINT + MY_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<MapTO> getUserOrganization() {
        return ResponseEntity.ok(orgService.getByUserId(AuthUtil.getUserName()));
    }
}
