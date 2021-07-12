package com.softron.schema.exam.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.ExamOrganization;
import com.softron.schema.config.VendorConfig;
import static com.softron.schema.queries.ExamQueries.*;

@Repository
public class ExamOrganizationRepositoryImpl implements ExamOrganizationRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VendorConfig vendorConfig;

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamOrganization> findByUserId(Long moduleId, String userId) {
		boolean runNative = vendorConfig.runNative();
		Query query = null;
		if (runNative) {
			query = entityManager.createNativeQuery(GET_USER_ORGS_QUERY_NATIVE, ExamOrganization.class);
		} else {
			query = entityManager.createQuery(GET_USER_ORGS_QUERY, ExamOrganization.class);
		}
		query.setParameter("moduleId", moduleId);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
