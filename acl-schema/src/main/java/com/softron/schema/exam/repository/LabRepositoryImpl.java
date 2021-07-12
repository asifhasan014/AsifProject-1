package com.softron.schema.exam.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softron.schema.config.VendorConfig;
import com.softron.schema.exam.entity.Lab;
import static com.softron.schema.queries.ExamQueries.*;

@Repository
public class LabRepositoryImpl implements LabRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VendorConfig vendorConfig;

	@SuppressWarnings("unchecked")
	@Override
	public List<Lab> findUserOrganizationLabs(Long moduleId, String userId) {
		boolean runNative = vendorConfig.runNative();
		Query query = null;
		if (runNative) {
			String sql = "SELECT l.* FROM LAB l WHERE l.ORGANIZATION_ID IN " + GET_USER_ORG_TREE_IDS_QUERY_NATIVE;
			query = entityManager.createNativeQuery(sql, Lab.class);
		} else {
			String sql = "Select l from Lab l where l.organization.id in " + GET_USER_ORG_TREE_IDS_QUERY;
			query = entityManager.createQuery(sql, Lab.class);
		}
		query.setParameter("moduleId", moduleId);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
