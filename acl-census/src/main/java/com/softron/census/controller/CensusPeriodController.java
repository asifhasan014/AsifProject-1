package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.CENSUS_PERIOD_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.text.ParseException;
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

import com.softron.census.domain.CensusPeriodTO;
import com.softron.census.helper.CensusPeriodHelper;
import com.softron.census.schema.entity.CensusPeriod;
import com.softron.census.service.CensusPeriodService;
import com.softron.core.annotations.ApiController;

@ApiController
public class CensusPeriodController {

    @Autowired
    private CensusPeriodService censusPeriodService;

    @GetMapping(value = CENSUS_PERIOD_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusPeriods() {
        return ResponseEntity.ok(censusPeriodService.getAllCensusPeriod());
    }

    @GetMapping(value = CENSUS_PERIOD_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCensusPeriods() {
        return ResponseEntity.ok(censusPeriodService.getAllActive());
    }

    @GetMapping(value = CENSUS_PERIOD_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusPeriodById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(CensusPeriodHelper.convertCensusPeriodToCensusPeriodModel(censusPeriodService.getCensusPeriodById(id)));
    }

    @DeleteMapping(value = CENSUS_PERIOD_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCensusPeriod(@PathVariable(ID) Long id) {
        try {
            censusPeriodService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = CENSUS_PERIOD_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCensusPeriod(@RequestBody CensusPeriodTO censusPeriodTO, @PathVariable(FLAG_EDIT) String flag) throws ParseException {
        List<CensusPeriod> censusPeriodTOs = censusPeriodService.findDuplicateCensusPeriod(censusPeriodTO.getCensusPeriod());
        if (!censusPeriodTOs.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (censusPeriodTOs.contains(censusPeriodTO.getCensusPeriod())) {
                    censusPeriodService.save(CensusPeriodHelper.convertCensusPeriodModelToCensusPeriod(censusPeriodTO));
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        } else {
            censusPeriodService.save(CensusPeriodHelper.convertCensusPeriodModelToCensusPeriod(censusPeriodTO));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = CENSUS_PERIOD_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCensusPeriod(@RequestBody CensusPeriodTO censusPeriodTO) throws ParseException {
        censusPeriodService.save(CensusPeriodHelper.convertCensusPeriodModelToCensusPeriod(censusPeriodTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
