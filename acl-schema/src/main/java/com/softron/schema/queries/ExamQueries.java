package com.softron.schema.queries;

public interface ExamQueries {

	/*
	 * **************** HQL Queries goes here ********************
	 */
	String USER_ORG_QUERY = "from ExamOrganization o" + " left join ExamOrganization o1 on o1.parentId = o.id"
			+ " where o.active=true and o.id in " + Queries.GET_USER_ORG_ID_QUERY;

	/**
	 * Query to get all organization ID, a user belongs to, searching by USER_ID and
	 * MODULE_ID.
	 */
	String GET_USER_ORG_TREE_IDS_QUERY = "(Select o.id " + USER_ORG_QUERY + ")";

	/**
	 * Query to get all organization data for the user.
	 */
	String GET_USER_ORGS_QUERY = "Select o " + USER_ORG_QUERY;

	/*
	 * ****************** Native Queries goes here **************
	 */
	String USER_ORG_QUERY_NATIVE = "FROM EXAM_ORGANIZATION o WHERE o.ACTIVE=1 AND o.ID IN "
			+ Queries.GET_USER_ORG_ID_QUERY_NATIVE
			+ " CONNECT BY PRIOR ID=PARENT_ORG_ID ORDER SIBLINGS BY DISPLAY_ORDER";

	/**
	 * Native Query to get all organization ID, a user belongs to, searching by
	 * USER_ID and MODULE_ID
	 */
	String GET_USER_ORG_TREE_IDS_QUERY_NATIVE = "(SELECT o.ID " + USER_ORG_QUERY_NATIVE + ")";

	/**
	 * Native Query to get all organization data for the user.
	 */
	String GET_USER_ORGS_QUERY_NATIVE = "SELECT o.* " + USER_ORG_QUERY_NATIVE;

}
