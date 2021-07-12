/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.softron.census.domain.CensusSearchTO;
import com.softron.census.schema.constant.OrgTypeEnum;
import com.softron.census.schema.entity.CensusDataEntry;

/**
 *
 * @author Mozahid
 */
public class CensusDataEntryFilter implements Specification<CensusDataEntry> {

	private static final long serialVersionUID = 4374502479703641247L;

	private CensusSearchTO filter;

	public CensusDataEntryFilter(CensusSearchTO filter) {
		super();
		this.filter = filter;
	}

	public Predicate toPredicate(Root<CensusDataEntry> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.join("censusPeriod").get("id"), filter.getPeriod()));

		if (filter.getOrgId() != null) {
			predicates.add(cb.equal(root.join("censusOrganization").get("id"), filter.getOrgId()));
		} else {
			predicates.add(cb.equal(root.join("censusOrganization").get("id"), filter.getMinistry()));
		}
		predicates.add(cb.equal(root.get("orgType"), OrgTypeEnum.from(filter.getOrgType())));
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
