package com.softron.reporting.repository.specification;

import com.softron.reporting.entity.Report;
import com.softron.reporting.to.SearchTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import static org.springframework.util.StringUtils.isEmpty;

public class ReportSpecification {

    private ReportSpecification() {

    }

    @SuppressWarnings("serial")
    public static Specification<Report> search(SearchTO search) {

        return new Specification<Report>() {

            @Override
            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (!isEmpty(search.getType())) {
                    predicates.add(cb.and(cb.equal(root.get("type"), search.getType())));
                }
                if (!isEmpty(search.getReportId())) {
                    predicates.add(cb.and(cb.equal(root.get("reportId"), search.getReportId())));
                }

                Predicate subDatePredicate = null;
                if (!isEmpty(search.getDateFrom()) && !isEmpty(search.getDateTo())) {
                    subDatePredicate = cb.between(root.<LocalDate>get("lastDateOfSubmission"), search.getDateFrom(), search.getDateTo());
                } else if (!isEmpty(search.getDateFrom())) {
                    subDatePredicate = cb.greaterThanOrEqualTo(root.<LocalDate>get("lastDateOfSubmission"), search.getDateFrom());
                } else if (!isEmpty(search.getDateTo())) {
                    subDatePredicate = cb.lessThanOrEqualTo(root.<LocalDate>get("lastDateOfSubmission"), search.getDateTo());
                }
                if (subDatePredicate != null) {
                    predicates.add(subDatePredicate);
                }
                predicates.addAll(defaultFilters(search, root, cb));
                query.orderBy(cb.desc(root.get("reportId")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    private static List<Predicate> defaultFilters(SearchTO search, Root<Report> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        switch (search.getFolder()) {
        case "compose":
            predicates.add(cb.equal(root.get("draft"), 1));
            predicates.add(cb.equal(root.get("sender"), search.getOrgId()));
            break;
        case "inbox":
            predicates.add(cb.equal(root.get("draft"), 0));
            predicates.add(cb.or(root.join("recipients", JoinType.LEFT).get("id").in(search.getOrgId()), root.join("acks", JoinType.LEFT).get("id").in(search.getOrgId())));
            break;
        case "sentbox":
            predicates.add(cb.equal(root.get("draft"), 0));
            predicates.add(cb.equal(root.get("sender"), search.getOrgId()));
            break;
        }
        return predicates;

    }

}
