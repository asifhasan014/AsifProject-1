/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.Post;
import com.softron.census.schema.entity.PostClass;
import com.softron.census.schema.filter.CensusPostSearchFilter;
import com.softron.census.schema.repository.PostRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Post post) {
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllActive() {
        List<Post> posts = postRepository.findAllActive();
        return posts;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPost() {
        List<Post> posts = postRepository.findAllPost();
        return posts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Post> findDuplicatePost(String name) {
        List<Post> posts = postRepository.findDuplicatePost(name);
        return posts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Post> getPostByClassId(Long id) {
        List<Post> posts = postRepository.getPostByClassId(id);
        return posts;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Post doesn't exist for id=" + id));
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPostFilter(PostClass postClass, String name) {
        Post post = new Post();
        post.setName(name);
        post.setPostClass(postClass);
        Specification<Post> specification = new CensusPostSearchFilter(post);
        List<Post> result = postRepository.findAll(specification);
        return result;
    }
}
