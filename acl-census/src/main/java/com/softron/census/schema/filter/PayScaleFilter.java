/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.softron.census.schema.entity.PayScale;

/**
 *
 * @author Mozahid
 */
public class PayScaleFilter implements Specification<PayScale> {

    private PayScale filter;

    public PayScaleFilter(PayScale filter) {
        super();
        this.filter = filter;
    }

    public Predicate toPredicate(Root<PayScale> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getPostClass() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("postClass"), filter.getPostClass()));
        }

        if (filter.getGrade() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("grade"), filter.getGrade()));
        }
        if (filter.getPayScaleYear() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("payScaleYear"), filter.getPayScaleYear()));
        }
        return p;
    }
}
