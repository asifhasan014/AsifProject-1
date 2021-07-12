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

import com.softron.census.schema.entity.CensusOfficeSearch;

/**
 *
 * @author Mozahid
 */
public class CensusOfficeSearchFilter implements Specification<CensusOfficeSearch> {

    private CensusOfficeSearch filter;

    public CensusOfficeSearchFilter(CensusOfficeSearch filter) {
        super();
        this.filter = filter;
    }

    public Predicate toPredicate(Root<CensusOfficeSearch> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getOfficeName() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("OfficeName"), filter.getOfficeName()));
        }

        return p;
    }

}
