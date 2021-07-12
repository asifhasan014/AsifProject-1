/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.POST_CLASS_ENDPOINT;
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

import com.softron.census.schema.entity.PostClass;
import com.softron.census.service.PostClassService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class PostClassController {

    @Autowired
    private PostClassService postClassService;

    @GetMapping(value = POST_CLASS_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPostClass() {
        return ResponseEntity.ok(postClassService.getAllPostClass());
    }

    @GetMapping(value = POST_CLASS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActivePostClass() {
        return ResponseEntity.ok(postClassService.getAllActive());
    }

    @GetMapping(value = POST_CLASS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getPostClassById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(postClassService.getPostClassById(id));
    }

    @DeleteMapping(value = POST_CLASS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deletePostClass(@PathVariable(ID) Long id) {

        try {
            postClassService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = POST_CLASS_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> savePostClass(@RequestBody PostClass postClass, @PathVariable(FLAG_EDIT) String flag) {
        List<PostClass> postClassList = postClassService.findDuplicateClass(postClass.getName());
        if (!postClassList.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                 if (!postClassList.contains(postClass.getName())) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    postClassService.save(postClass);
                }
            }
        } else {
            postClassService.save(postClass);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = POST_CLASS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updatePostClass(@RequestBody PostClass postClass) {
        postClassService.save(postClass);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
