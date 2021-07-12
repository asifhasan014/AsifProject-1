package com.softron.core.constants;

import org.springframework.http.MediaType;

public interface ExamApiConstants {

    String EXTERNAL_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    String API_ENDPOINT = "/api";

    String PUBLIC_ENDPOINT = "/public";

    String ID = "id";

    String CANDIDATE_ID = "candidateId";

    String EXAM_ID = "examId";

    String EXAM_SESSION_ID = "examSessionId";

    String CANDIDATE_DETAIL_PATH_VAR = "/{" + CANDIDATE_ID + "}/{" + EXAM_ID + "}";

    String CANDIDATE_EXAM_TEXT_DETAIL_PATH_VAR = "/{" + CANDIDATE_ID + "}";

    String CANDIDATE_EXAM_LIST_PATH_VAR = "/{" + EXAM_ID + "}/{" + EXAM_SESSION_ID + "}";

    String ID_PATH_VAR = "/{" + ID + "}";

    String EXAM_ID_PATH_VAR = "/{" + EXAM_ID + "}";

    String ALL_VERB = ":all";

    String TREE_VERB = ":tree";

    String LIST_VERB = ":list";

    String BY_MODULE_VERB = ":bymodule";
    
    String BY_USER_VERB = ":byuser";

    String CHILDREN = "/children";

    String FILTER = "/filter";

    String DESIGNATION = "/designation";

    String DESIGNATION_BY_EXAM_ID = "/findByExamId";

    String DESIGNATION_ENDPOINT = API_ENDPOINT + "/designation";

    String CANDIDATE_LOGIN_ENDPOINT = "/candidate/login";

    String CANDIDATE_DETAILS_ENDPOINT = "/candidate/candidatedetails";

    String SAVE_CANDIDATE_EXAM_DETAIL_ENDPOINT = "/candidateexamdetail/save";

    String CANDIDATE_EXAM_RESULT_ENDPOINT = API_ENDPOINT + "/candidateexamdetail/examresult";

    String CANDIDATE_EXAM_TEXT_DETAILS_ENDPOINT = "/candidateexamdetail/examtestdetails";

    String CANDIDATE_EXAM_RESULT_LIST_ENDPOINT = API_ENDPOINT + "/candidateexamdetail/candidateresultlist";

    String CANDIDATE_EXAM_RESULT_DETAILS_FILE = API_ENDPOINT + "/candidateexamdetail/resultdetailsfile";

    String USERS_ENDPOINT = API_ENDPOINT + "/users";
    
    String EXAM_API_ENDPOINT = API_ENDPOINT + "/exam";

    String EXAM_ORG_ENDPOINT = EXAM_API_ENDPOINT + "/organization";

    String ORGTYPE_ENDPOINT = EXAM_ORG_ENDPOINT + "/orgtype";
    
    String OFFICETYPE_ENDPOINT = EXAM_ORG_ENDPOINT + "/officetype";
    
    String ORG_TREEVIEW_ENDPOINT = EXAM_ORG_ENDPOINT + "/treeview";

    String OFFICE_TYPE_BY_ORG_TYPE = "/officetypebyorgtype";
    
    String ORGANIZATION_TYPE = "orgtype";

    String ORGANIZATION_TYPE_PATH_VAR = "/{" + ORGANIZATION_TYPE + "}";

	String LAB_API_ENDPOINT = EXAM_API_ENDPOINT + "/lab";

    String LAB_PC_FILE_ENDPOINT = LAB_API_ENDPOINT + "/pcfile";

    String LAB_FROM_ORG_ENDPOINT = LAB_API_ENDPOINT + "/labfromorg";

    String ORG_BY_MINISTRY_ENDPOINT = API_ENDPOINT + "/exam/organization/ministry";

    String EXAM_SESSION_LIST = "/examSessionList";

    String EXAM_SESSION_LIST_BY_EXAM_ID = "/examSessionListByExamId" + EXAM_ID_PATH_VAR;

    String EXAM_PUBLIC_ENDPOINT = "/public/exam";

    String CANDIDATE_FILE_ENDPOINT = EXAM_API_ENDPOINT + "/candidatefile";

    String EXAM_DETAILS_ENDPOINT = EXAM_API_ENDPOINT + "/examdetails";
}
