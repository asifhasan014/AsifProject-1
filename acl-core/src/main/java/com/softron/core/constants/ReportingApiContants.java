package com.softron.core.constants;

public interface ReportingApiContants extends ApiConstants {

    String OR_ENDPOINT = API_ENDPOINT + "/reporting";

    String OR_TYPES_ENDPOINT = OR_ENDPOINT + "/types";

    String OR_ORGS_ENDPOINT = OR_ENDPOINT + "/orgs";

    String OR_ORG_TYPE_ENDPOINT = OR_ENDPOINT + "/orgtype";

    String OR_REPORT_ENDPOINT = OR_ENDPOINT + "/report";
    
    String OR_SEARCH_ENDPOINT = OR_ENDPOINT + "/search";

    String OR_ORG_MAPPING_ENDPOINT = OR_ORGS_ENDPOINT + "/mappings";

    String OR_ATTACH_ENDPOINT = OR_REPORT_ENDPOINT + ID_PATH_VAR + ":attch";

    String OR_SENDER_ATTACH_ENDPOINT = OR_REPORT_ENDPOINT + ID_PATH_VAR + "/sender" + ":attch";
    
    String OR_REPLY_ATTACH_ENDPOINT = OR_REPORT_ENDPOINT + ID_PATH_VAR + "/reply" + ":attch";

    String OR_RECIPIENT_ENDPOINT = OR_REPORT_ENDPOINT + ID_PATH_VAR + ":rcpt";

    String OR_ACKS_ENDPOINT = OR_REPORT_ENDPOINT + ID_PATH_VAR + ":acks";
    
    String OR_SUBMIT_ENDPOINT = OR_REPORT_ENDPOINT + ID_PATH_VAR + ":submit";

    String OR_ATTCH_DOWNLOAD_ENDPOINT = OR_ATTACH_ENDPOINT + "/download" + CHILD_ID_PATH_VAR;

}
