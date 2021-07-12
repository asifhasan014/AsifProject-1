/*
 * To change this license header, choose License Headers in Post Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.softron.census.schema.entity.Post;

/**
 *
 * @author Mozahid
 */
public class CensusPostSearchFilter implements Specification<Post> {

    private Post filter;

    public CensusPostSearchFilter(Post filter) {
        super();
        this.filter = filter;
    }

    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getName() != null) {
            p.getExpressions().add(cb.equal(root.get("name"), filter.getName()));
        }

        if (filter.getPostClass() != null) {
            p.getExpressions().add(cb.equal(root.get("postClass"), filter.getPostClass()));
        }
        return p;
    }
}
