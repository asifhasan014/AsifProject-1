/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT;
import static com.softron.census.constant.WebApiConstants.FLAG_EDIT_PATH_VAR;
import static com.softron.census.constant.WebApiConstants.POST_ENDPOINT;
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

import com.softron.census.schema.entity.Post;
import com.softron.census.service.PostService;
import com.softron.core.annotations.ApiController;

/**
 *
 * @author Mozahid
 */
@ApiController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = POST_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPost() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping(value = POST_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActivePost() {
        return ResponseEntity.ok(postService.getAllActive());
    }

    @GetMapping(value = POST_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getPostById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping(value = POST_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deletePost(@PathVariable(ID) Long id) {

        try {
            postService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = POST_ENDPOINT + FLAG_EDIT_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> savePost(@RequestBody Post post, @PathVariable(FLAG_EDIT) String flag) {
        List<Post> posts = postService.findDuplicatePost(post.getName());
        if (!posts.isEmpty()) {
            if (flag.equals("0")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                 if (!posts.contains(post.getName())) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    postService.save(post);
                }
            }
        } else {
            postService.save(post);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(value = POST_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updatePost(@RequestBody Post censusPostSearch) {
        postService.save(censusPostSearch);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = POST_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllPost(@RequestBody Post censusPostSearch) {
        return ResponseEntity.ok(postService.getAllPostFilter(censusPostSearch.getPostClass(), censusPostSearch.getName()));
    }
}
