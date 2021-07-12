/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.PAY_SCALE_YEAR_ENDPOINT;
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

import com.softron.census.schema.entity.PayScaleYear;
import com.softron.census.service.PayScaleYearService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class PayScaleYearController {

    @Autowired
    private PayScaleYearService payScaleYearService;

    @GetMapping(value = PAY_SCALE_YEAR_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPayScaleYears() {
        return ResponseEntity.ok(payScaleYearService.getAllPayScaleYear());
    }

    @GetMapping(value = PAY_SCALE_YEAR_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActivePayScaleYears() {
        return ResponseEntity.ok(payScaleYearService.getAllActive());
    }

    @GetMapping(value = PAY_SCALE_YEAR_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getPayScaleYearById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(payScaleYearService.getPayScaleYearById(id));
    }

    @DeleteMapping(value = PAY_SCALE_YEAR_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deletePayScaleYear(@PathVariable(ID) Long id) {
        try {
            payScaleYearService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = PAY_SCALE_YEAR_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> savePayScaleYear(@RequestBody PayScaleYear payScaleYear, @PathVariable(FLAG_EDIT) String flag) {
        List<PayScaleYear> payScaleYears = payScaleYearService.findDuplicatePayScaleYear(payScaleYear.getName());
        if (!payScaleYears.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                payScaleYearService.save(payScaleYear);
            }
        } else {
            payScaleYearService.save(payScaleYear);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = PAY_SCALE_YEAR_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updatePayScaleYear(@RequestBody PayScaleYear payScaleYear) {
        payScaleYearService.save(payScaleYear);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
