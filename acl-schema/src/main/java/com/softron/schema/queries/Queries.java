package com.softron.schema.queries;

import com.softron.core.constants.CommonTableNameConstants;

public interface Queries {

	/**
	 * Query to get the organization IDs, a user is mapped with.
	 */
	String GET_USER_ORG_ID_QUERY = "(Select um.orgId from OrgUsersMapping um where um.moduleId = :moduleId and um.userId = :userId)";

	/**
	 * Native query to get the organization IDs, a user is mapped with.
	 */
	String GET_USER_ORG_ID_QUERY_NATIVE = "(SELECT um.ORG_ID FROM " + CommonTableNameConstants.USER_MAPPING_TABLE
			+ " um WHERE um.MODULE_ID = :moduleId AND um.USER_ID = :userId)";

}