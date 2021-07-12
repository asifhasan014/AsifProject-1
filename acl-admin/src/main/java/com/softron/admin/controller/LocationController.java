package com.softron.admin.controller;

import com.softron.admin.service.AdminService;
import com.softron.common.transferobjects.RawTree;
import com.softron.common.transferobjects.TreeTO;
import com.softron.common.utils.TreeBuilder;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Location;

import static com.softron.core.constants.AdminApiConstants.LOCATION_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.CHILDREN;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ApiConstants.TREE_VERB;

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
public class LocationController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = LOCATION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getLocations() {
        return ResponseEntity.ok(adminService.getActiveLocations());
    }

    @GetMapping(value = LOCATION_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok(adminService.getAllLocations());
    }

    @GetMapping(value = LOCATION_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getLocationById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getLocationById(id));
    }

    @DeleteMapping(value = LOCATION_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteLocation(@PathVariable(ID) Long id) {
        adminService.deleteLocation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = LOCATION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveLocation(@RequestBody Location loc) {
        adminService.saveLocation(loc);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = LOCATION_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateLocation(@RequestBody Location loc) {
        adminService.saveLocation(loc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = LOCATION_ENDPOINT + TREE_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public List<TreeTO> getLocationTree() {
        List<Location> locations = adminService.getActiveLocations();
        List<RawTree> rawData = locations.stream().map(l -> new RawTree(l.getId(), l.getNameEng(), l.getParentId())).collect(toList());
        return TreeBuilder.build(rawData);
    }

    @GetMapping(value = LOCATION_ENDPOINT + ID_PATH_VAR + CHILDREN, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getChildren(@PathVariable(ID) Long parentId) {
        List<Location> locations = adminService.getChildLocations(parentId);
        return ResponseEntity.ok(locations);
    }
}
