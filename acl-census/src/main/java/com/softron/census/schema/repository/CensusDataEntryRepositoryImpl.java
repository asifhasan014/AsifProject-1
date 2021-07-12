package com.softron.census.schema.repository;

import static com.softron.schema.queries.CensusQueries.GET_USER_ORG_TREE_IDS_QUERY;
import static com.softron.schema.queries.CensusQueries.GET_USER_ORG_TREE_IDS_QUERY_NATIVE;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.CensusDataEntry;
import com.softron.schema.config.VendorConfig;

@Repository
public class CensusDataEntryRepositoryImpl implements CensusDataEntryRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VendorConfig vendorConfig;

	@SuppressWarnings("unchecked")
	@Override
	public List<CensusDataEntry> findAllCensusDataEntryByUserOrg(Long moduleId, String userId) {
		boolean runNative = vendorConfig.runNative();
		Query query = null;
		if (runNative) {
			String sql = "SELECT c.* FROM CENSUS_DATA_ENTRY c WHERE c.ACTIVE=1 AND c.ORGANIZATION_ID IN "
					+ GET_USER_ORG_TREE_IDS_QUERY_NATIVE;
			query = entityManager.createNativeQuery(sql, CensusDataEntry.class);
		} else {
			String sql = "Select c from CensusDataEntry c where c.active=true and c.censusOrganization.id in "
					+ GET_USER_ORG_TREE_IDS_QUERY;
			query = entityManager.createQuery(sql, CensusDataEntry.class);
		}
		query.setParameter("moduleId", moduleId);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
