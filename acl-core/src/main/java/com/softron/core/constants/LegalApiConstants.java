package com.softron.core.constants;

public interface LegalApiConstants extends ApiConstants {
    
    String LEGAL_STATS_ENDPOINT = API_ENDPOINT + "/legal/stats";

    String LEGAL_CASE_ENDPOINT = API_ENDPOINT + "/legal/case";

    String HEARING_ENDPOINT = LEGAL_CASE_ENDPOINT + "/hrng";

    String RUNNING_CASE_ENDPOINT = LEGAL_CASE_ENDPOINT + "/rc";

    String LEGAL_CASE_SEARCH_ENDPOINT = LEGAL_CASE_ENDPOINT + "/search";

    String LEG_ADVS_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":advs";

    String LEG_RESP_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":resp";

    String LEG_PETS_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":pets";

    String LEG_ATTACH_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":attch";

    String LEG_HEARING_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":hrng";

    String LEG_RC_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":rc";

    String LEG_SF_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":sf";

    String LEG_LINK_ENDPOINT = LEGAL_CASE_ENDPOINT + ID_PATH_VAR + ":link";

    String LEG_ATTCH_DOWNLOAD_ENDPOINT = LEG_ATTACH_ENDPOINT + "/download" + CHILD_ID_PATH_VAR;

    String LEG_SF_DOWNLOAD_ENDPOINT = LEG_SF_ENDPOINT + "/download" + CHILD_ID_PATH_VAR;
}