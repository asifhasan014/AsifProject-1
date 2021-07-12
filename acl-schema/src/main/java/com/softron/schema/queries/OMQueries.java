package com.softron.schema.queries;

public interface OMQueries {

	String GET_USER_ORG_TREE_IDS_QUERY = "(Select o.id from Org o left join Org o1 on o1.parentId = o.id "
			+ "where o.active=true and o.id in " + Queries.GET_USER_ORG_ID_QUERY + ")";

	String GET_USER_ORG_TREE_IDS_QUERY_NATIVE = "(SELECT o.ID FROM ORG_ORGANIZATION o WHERE o.ACTIVE=1 AND o.ID IN "
			+ Queries.GET_USER_ORG_ID_QUERY_NATIVE
			+ ") CONNECT BY PRIOR ID=PARENT_ORG_ID ORDER SIBLINGS BY CENSUS_ORDER";
}
