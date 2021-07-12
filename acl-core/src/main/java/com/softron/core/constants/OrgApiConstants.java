package com.softron.core.constants;

public interface OrgApiConstants extends ApiConstants {



    String ORG_GRADE = "/grade";

    String CREATE = "/create";

    String LIST = "/list";

    String ORG_GRADE_ENDPOINT = ORG_ENDPOINT + "/grade";

    String ORG_ORGANIZATION_ENDPOINT = ORG_ENDPOINT + "/organization";
    
    String ORG_POST_SETUP_ENDPOINT = ORG_ENDPOINT + "/post-setup";

    String ORG_POST_SETUP_CREATE_ENDPOINT = ORG_POST_SETUP_ENDPOINT + "/create";

    String ORG_POST_ENDPOINT = ORG_ENDPOINT + "/post";

    String ORG_CHANGE_POST_ENDPOINT = ORG_ENDPOINT + "/change-post";

    String ORG_INSTRUMENT_TYPE_ENDPOINT = ORG_ENDPOINT + "/instrument-type";

    String ORG_ATTACHMENT_ENDPOINT = ORG_ENDPOINT + "/attachment";

    String ORG_DOC_TYPE_ENDPOINT = ORG_ATTACHMENT_ENDPOINT + "/doc-type";

    String ORG_VACANT_POST_ENDPOINT = ORG_ENDPOINT + "/vacant";

    String ORG_UPGRADE_ENDPOINT = ORG_ENDPOINT + "/post-upgrade";

    String ORG_RESERVATION_ENDPOINT = ORG_ENDPOINT + "/post-reservation";

    String ORG_ABOLITION_ENDPOINT = ORG_ENDPOINT + "/post-abolition";

    String ORG_PERMANENT_ENDPOINT = ORG_ENDPOINT + "/post-permanent";

    String ORG_DOC_SUB_TYPE_ENDPOINT = ORG_ATTACHMENT_ENDPOINT + "/doc-sub-type";

    String ORG_TRANSPORT_ENDPOINT = ORG_ENDPOINT + "/transport";

    String ORG_TRANSPORT_TYPE_ENDPOINT = ORG_TRANSPORT_ENDPOINT + "/transport-type";

    String ORG_INSTRUMENT_ENDPOINT = ORG_ENDPOINT + "/instrument";

    String ORG_POST_DETAIL_ENDPOINT = ORG_ENDPOINT + "/post-detail";

    String ORG_POST_DETAIL_SEARCH = ORG_ENDPOINT + "/post-detail/search";

    String ORG_ENAM_COMMITTEE_POST_SAVE_ENDPOINT = ORG_POST_DETAIL_ENDPOINT + "/saveEnamCommitteePost";

    String ORG_OTHER_POST_SAVE_ENDPOINT = ORG_POST_DETAIL_ENDPOINT + "/saveOtherPost";

    String ORG_GET_ALL_POST_BY_ENAM_COMMITTEE= ORG_POST_DETAIL_ENDPOINT + "/getPostByEnamCommittee";

    String ORG_GET_ALL_POST= ORG_POST_DETAIL_ENDPOINT + "/getPostsByOther";

    String ORG_ALL_POST_BY_ORG = ORG_POST_DETAIL_ENDPOINT + LIST + "/{" + ID + "}";

    String ORG_ALL_POST_LIST_ENDPOINT = ORG_POST_DETAIL_ENDPOINT + LIST;

    String ORG_ALL_ORGS__ENDPOINT = ORG_POST_DETAIL_ENDPOINT + "/orgs";

    String ORG_TRANSPORTATION_ENDPOINT = API_ENDPOINT + "/transportation";

    String ORG_ALL_MINISTRY = ORG_ORGANIZATION_ENDPOINT + "/allministry";

    String ORG_ALL_ORG_BY_PARENT_ID = ORG_ORGANIZATION_ENDPOINT + "/allorgbyparentid/{" + ID + "}/{" + ORG_TYPE + "}";
    
    String ORG_ALL_ORG = ORG_ORGANIZATION_ENDPOINT + "/allOrg/childTree";

    String CHILD_ORG_BY_PARENT_ID = ORG_ORGANIZATION_ENDPOINT + "/childOrganization/{" + ID + "}";
    
    String CHILD_ORG_BY_PARENT_ID_NEW = ORG_ORGANIZATION_ENDPOINT + "/childOrganizationNew/{" + ID + "}";

    String CHILD_ORG_BY_PARENT_ID1 = ORG_ORGANIZATION_ENDPOINT + "/childOrganization1/{" + ID + "}";
    
    String OM_ORG_TYPE_ENDPOINT = ORG_ORGANIZATION_ENDPOINT + "/orgType";
    
    String ORG_USER_ORG_TREE = ORG_ORGANIZATION_ENDPOINT + BY_USER_VERB + TREE_VERB;
}
