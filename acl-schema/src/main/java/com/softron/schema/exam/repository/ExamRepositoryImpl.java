package com.softron.schema.exam.repository;

import static com.softron.schema.queries.ExamQueries.GET_USER_ORG_TREE_IDS_QUERY;
import static com.softron.schema.queries.ExamQueries.GET_USER_ORG_TREE_IDS_QUERY_NATIVE;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softron.schema.config.VendorConfig;
import com.softron.schema.exam.entity.Exam;

@Repository
public class ExamRepositoryImpl implements ExamRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VendorConfig vendorConfig;

	@SuppressWarnings("unchecked")
	@Override
	public List<Exam> findUserOrganizationExams(Long moduleId, String userId) {
		final boolean runNative = vendorConfig.runNative();
		Query query = null;
		if (runNative) {
			String sqlQuery = "SELECT e FROM EXAM e WHERE e.ORGANIZATION_ID in " + GET_USER_ORG_TREE_IDS_QUERY_NATIVE;
			query = entityManager.createNativeQuery(sqlQuery, Exam.class);
		} else {
			String sqlQuery = "Select e From Exam e WHERE e.organization.id in " + GET_USER_ORG_TREE_IDS_QUERY;
			query = entityManager.createQuery(sqlQuery, Exam.class);
		}
		query.setParameter("moduleId", moduleId);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
