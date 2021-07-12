/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.PAY_SCALE_ENDPOINT;
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

import com.softron.census.schema.entity.PayScale;
import com.softron.census.service.PayScaleService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class PayScaleController {

    @Autowired
    private PayScaleService payScaleService;

    @GetMapping(value = PAY_SCALE_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPayScales() {
        return ResponseEntity.ok(payScaleService.getAllPayScale());
    }

    @GetMapping(value = PAY_SCALE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActivePayScales() {
        return ResponseEntity.ok(payScaleService.getAllActive());
    }

    @GetMapping(value = PAY_SCALE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getPayScaleById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(payScaleService.getPayScaleById(id));
    }

    @DeleteMapping(value = PAY_SCALE_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deletePayScale(@PathVariable(ID) Long id) {
        try {
            payScaleService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = PAY_SCALE_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> savePayScale(@RequestBody PayScale payScale, @PathVariable(FLAG_EDIT) String flag) {
        List<PayScale> payScales = payScaleService.findDuplicatePayScale(payScale.getPayScaleYear().getId(), payScale.getPostClass().getId(), payScale.getGrade().getId(), payScale.getName());

        if (!payScales.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (!payScales.contains(payScale.getPayScaleYear().getId()) && !payScales.contains(payScale.getPostClass().getId()) && !payScales.contains(payScale.getGrade().getId()) && !payScales.contains(payScale.getPayScaleYear().getId()) && !payScales.contains(payScale.getName())) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    payScaleService.save(payScale);
                }
            }
        } else {
            payScaleService.save(payScale);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = PAY_SCALE_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updatePayScale(@RequestBody PayScale payScale) {
        payScaleService.save(payScale);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = PAY_SCALE_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPayScale(@RequestBody PayScale payScale) {
        return ResponseEntity.ok(payScaleService.getAllPayScaleFilter(payScale.getPostClass(), payScale.getGrade(), payScale.getPayScaleYear()));
    }
}
