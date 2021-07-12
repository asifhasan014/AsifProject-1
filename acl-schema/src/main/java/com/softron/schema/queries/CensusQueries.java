package com.softron.schema.queries;

public interface CensusQueries {

	/**
	 * Query to Get all organization and it's child organizations for a User
	 * searching by USER_ID and MODULE_ID.
	 */
	String GET_USER_ORG_TREE_IDS_QUERY = "(Select o.id from CensusOrganization o left join CensusOrganization o1 on o1.parentId = o.id "
			+ "where o.active=true and o.id in " + Queries.GET_USER_ORG_ID_QUERY + ")";

	/**
	 * Native Query to Get all organization and it's child organizations for a User
	 * searching by USER_ID and MODULE_ID.
	 */
	String GET_USER_ORG_TREE_IDS_QUERY_NATIVE = "(SELECT o.ID FROM CENSUS_ORGANIZATION o WHERE o.ACTIVE=1 AND o.ID IN "
			+ Queries.GET_USER_ORG_ID_QUERY_NATIVE
			+ " CONNECT BY PRIOR ID=PARENT_ORG_ID ORDER SIBLINGS BY CENSUS_ORDER)";

}
