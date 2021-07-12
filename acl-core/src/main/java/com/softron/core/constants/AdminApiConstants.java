package com.softron.core.constants;

public interface AdminApiConstants extends ApiConstants {

	String MODULE_ENDPOINT = API_ENDPOINT + "/module";

	String MODULE_USERS_ENDPOINT = API_ENDPOINT + "/module" + ID_PATH_VAR + "/org" + CHILD_ID_PATH_VAR + "/users";

	String MODULE_ORG_USER_MAPPING_ENDPOINT = MODULE_ENDPOINT + ID_PATH_VAR + "/org" + CHILD_ID_PATH_VAR + "/mappings";

	String MENU_ENDPOINT = API_ENDPOINT + "/menu";
	
	String MENU_ORDERING_ENDPOINT = MENU_ENDPOINT + ID_PATH_VAR + "/ordering";

	String USERS_ENDPOINT = API_ENDPOINT + "/users";

	String ROLES_ENDPOINT = API_ENDPOINT + "/roles";

	String ADVOCATE_ENDPOINT = API_ENDPOINT + "/advocate";

	String CASE_TYPE_ENDPOINT = API_ENDPOINT + "/casetype";

	String COURT_ENDPOINT = API_ENDPOINT + "/court";

	String PETITIONER_ENDPOINT = API_ENDPOINT + "/petitioner";

	String RESPONDENT_ENDPOINT = API_ENDPOINT + "/respondent";

	String STATUS_ENDPOINT = API_ENDPOINT + "/status";

	String LOCATION_ENDPOINT = API_ENDPOINT + "/location";

	String ORG_ENDPOINT = API_ENDPOINT + "/org";
	
	String ORG_ENDPOINTWITHSETTINGS = API_ENDPOINT + "/organizationsettings";

	String ORG_TYPE_ENDPOINT = API_ENDPOINT + "/orgtype";
	
	String LOGGED_USER_ORG_ENDPOINT = API_ENDPOINT + "/loggedUserOrg";
	
	String ORG_AS_COMPANY = API_ENDPOINT + "/getOrgAsCompany";
	
}
