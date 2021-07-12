package com.softron.census.schema.repository;

import static com.softron.schema.queries.CensusQueries.GET_USER_ORG_TREE_IDS_QUERY;
import static com.softron.schema.queries.CensusQueries.GET_USER_ORG_TREE_IDS_QUERY_NATIVE;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.CensusOrganization;
import com.softron.schema.config.VendorConfig;

@Repository
public class CensusOrganizationRepositoryImpl implements CensusOrganizationRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VendorConfig vendorConfig;

	@SuppressWarnings("unchecked")
	@Override
	public List<CensusOrganization> findUserOrganizationByMinistry(Long moduleId, String userId) {

		boolean runNative = vendorConfig.runNative();
		Query query = null;
		if (runNative) {
			String sql = "SELECT c.* FROM CENSUS_ORGANIZATION c WHERE c.ORG_TYPE= 'MINISTRY' and c.id IN "
					+ GET_USER_ORG_TREE_IDS_QUERY_NATIVE;
			query = entityManager.createNativeQuery(sql, CensusOrganization.class);
		} else {
			String sql = "Select c from CensusOrganization c where c.orgType= 'MINISTRY' and c.id in "
					+ GET_USER_ORG_TREE_IDS_QUERY;
			query = entityManager.createQuery(sql, CensusOrganization.class);
		}
		query.setParameter("moduleId", moduleId);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
