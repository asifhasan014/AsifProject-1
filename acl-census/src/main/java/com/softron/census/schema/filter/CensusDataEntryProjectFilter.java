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

import com.softron.census.schema.entity.CensusDataEntryProject;

/**
 *
 * @author Mozahid
 */
public class CensusDataEntryProjectFilter implements Specification<CensusDataEntryProject> {

    private CensusDataEntryProject filter;

    public CensusDataEntryProjectFilter(CensusDataEntryProject filter) {
        super();
        this.filter = filter;
    }

    public Predicate toPredicate(Root<CensusDataEntryProject> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getCensusPeriod() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("censusPeriod"), filter.getCensusPeriod()));
        }

        if (filter.getProject() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("project"), filter.getProject()));
        }
        if (filter.getPost() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("post"), filter.getPost()));
        }
        if (filter.getCensusOrganization() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("censusOrganization"), filter.getCensusOrganization()));
        }
        return p;
    }
}
