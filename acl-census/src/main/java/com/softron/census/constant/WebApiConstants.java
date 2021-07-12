/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.constant;

import com.softron.core.constants.ApiConstants;

/**
 *
 * @author Mozahid
 */
public interface WebApiConstants extends ApiConstants {

    String FILTER = "/filter";

    String DROPDOWN_CHANGE = "/dropdown";

    String EXCEL_REPORT = "/excelreport";

    String PDF_REPORT = "/pdfreport";

    String DISTRICT_ENDPOINT = API_ENDPOINT + "/district";

    String DIVISION = "/division";

    String DIVISION_ENDPOINT = API_ENDPOINT + "/division";

    String PROJECT_TYPE_ENDPOINT = API_ENDPOINT + "/projecttype";

    String SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE = "/summaryofmanpowerofall";

    String SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE_EXCEL_REPORT = "/summaryofmanpowerofall" + EXCEL_REPORT;

    String SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE_PDF_REPORT = "/summaryofmanpowerofall" + PDF_REPORT;

    String CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF = "/civilofficerandstaffreport";

    String CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF_EXCEL_REPORT = "/civilofficerandstaffreport" + EXCEL_REPORT;

    String CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF_PDF_REPORT = "/civilofficerandstaffreport" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL = "/classwisestaticsofprojectpersonnel";

    String CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL_FOR_EXCEL = "/classwisestaticsofprojectpersonnel" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL_FOR_PDF = "/classwisestaticsofprojectpersonnel" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF = "/classwisestaticsoffemaleofficersandstaffdata";

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE = "/classwisestaticsofofficerandstaffofdepartmentanddirectorate";

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE_FOR_EXCEL = "/classwisestaticsofofficerandstaffofdepartmentanddirectorate" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE_FOR_PDF = "/classwisestaticsofofficerandstaffofdepartmentanddirectorate" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF_FOR_EXCEL = "/classwisestaticsoffemaleofficersandstaffdata" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF_FOR_PDF = "/classwisestaticsoffemaleofficersandstaffdata" + PDF_REPORT;

    String CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE = "/class1officerofdivcommdepcommbypayscale";

    String CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE_FOR_EXCEL = "/class1officerofdivcommdepcommbypayscale" + EXCEL_REPORT;

    String CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE_FOR_PDF = "/class1officerofdivcommdepcommbypayscale" + PDF_REPORT;

    String CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE = "/class1officerofministriesdivisionbypayscale";

    String CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE_FOR_EXCEL = "/class1officerofministriesdivisionbypayscale" + EXCEL_REPORT;

    String CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE_FOR_PDF = "/class1officerofministriesdivisionbypayscale" + PDF_REPORT;

    String CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE = "/class1officerofdepartmentanddirectoratepayscale";

    String CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE_FOR_EXCEL = "/class1officerofdepartmentanddirectoratepayscale" + EXCEL_REPORT;

    String CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE_FOR_PDF = "/class1officerofdepartmentanddirectoratepayscale" + PDF_REPORT;

    String CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE = "/class1officerofautonomousbodiesandcorporationpayscale";

    String CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE_FOR_EXCEL = "/class1officerofautonomousbodiesandcorporationpayscale" + EXCEL_REPORT;

    String CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE_FOR_PDF = "/class1officerofautonomousbodiesandcorporationpayscale" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION = "/classwisestaticsofmaleandfemaleofministryanddivision";

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION_FOR_PDF = "/classwisestaticsofmaleandfemaleofministryanddivision" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION_FOR_EXCEL = "/classwisestaticsofmaleandfemaleofministryanddivision" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION = "/classwisestaticsofofficerandstaffofministryanddivision";

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION_FOR_PDF = "/classwisestaticsofofficerandstaffofministryanddivision" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION_FOR_EXCEL = "/classwisestaticsofofficerandstaffofministryanddivision" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION = "/classwisestaticsofofficerandstaffofautonomousbodiescorporation";

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION_FOR_PDF = "/classwisestaticsofofficerandstaffofautonomousbodiescorporation" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION_FOR_EXCEL = "/classwisestaticsofofficerandstaffofautonomousbodiescorporation" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM = "/classwisestaticsofofficerandstaffofdivisionalcommanddeputycomm";

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM_FOR_PDF = "/classwisestaticsofofficerandstaffofdivisionalcommanddeputycomm" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM_FOR_EXCEL = "/classwisestaticsofofficerandstaffofdivisionalcommanddeputycomm" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION = "/classwisestaticsofmaleandfemaleofautonomousbodiescorporation";

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION_FOR_PDF = "/classwisestaticsofmaleandfemaleofautonomousbodiescorporation" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION_FOR_EXCEL = "/classwisestaticsofmaleandfemaleofautonomousbodiescorporation" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER = "/classwisestaticsofmaleandfemaleofdivisionalcommissioneranddeputycommo";

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER_FOR_PDF = "/classwisestaticsofmaleandfemaleofdivisionalcommissioneranddeputycommo" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER_FOR_EXCEL = "/classwisestaticsofmaleandfemaleofdivisionalcommissioneranddeputycommo" + EXCEL_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE = "/classwisestaticsofmaleandfemaleofdepartmentanddirectorate";

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE_FOR_PDF = "/classwisestaticsofmaleandfemaleofdepartmentanddirectorate" + PDF_REPORT;

    String CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE_FOR_EXCEL = "/classwisestaticsofmaleandfemaleofdepartmentanddirectorate" + EXCEL_REPORT;

    String ENUM = "/censusenum";

    String URL_ENUM = "/censusurlenum";

    String CHART_URL_ENUM = "/censuscharturlenum";

    String OFFICE_TYPE_ENUM = "/officetypeenum";

    String BAR_CHART_FOR_MANPOWER_OF_ALL_ORG = "/barchartdata";

    String BAR_CHART_FOR_MINISTRIES_AND_DIVISION = "/barchartforministriesanddivision";

    String BAR_CHART_FOR_DEPARTMENT_AND_DIRECTORATE = "/barchartfordepartmentanddirectorate";

    String BAR_CHART_FOR_AUTONOMOUS_BODIES_AND_CORPORATION = "/barchartforautonomousbodiesandcorporation";

    String BAR_CHART_FOR_DIVISIONAL_COMM_AND_DEPUTY_COMM = "/barchartfordivisionalcommanddeputycomm";

    String ORG_TYPE_ENUM = "/orgtypeenum";

    String ALL_MINISTRY = "/allministry";

    String ALL_ORG_BY_PARENT_ID = "/allorgbyparentid/{" + ID + "}/{" + ORG_TYPE + "}";

    String CENSUS_PERIOD_ENDPOINT = API_ENDPOINT + "/censusperiod";

    String PAY_SCALE_YEAR_ENDPOINT = API_ENDPOINT + "/payscaleyear";

    String OFFICE_SEARCH_ENDPOINT = API_ENDPOINT + "/officesearch";

    String OFFICE_TYPE_ENDPOINT = API_ENDPOINT + "/officetype";

    String POST_CLASS_ENDPOINT = API_ENDPOINT + "/postclass";

    String PROJECT_ENDPOINT = API_ENDPOINT + "/project";

    String MINISTRY_ENDPOINT = API_ENDPOINT + "/ministry";

    String GRADE_ENDPOINT = API_ENDPOINT + "/grade";

    String PAY_SCALE_ENDPOINT = API_ENDPOINT + "/payscale";

    String DEPARTMENT_DIRECTORATE_ENDPOINT = API_ENDPOINT + "/departmentdirectorate";

    String AUTONOMOUS_CORPORATION_ENDPOINT = API_ENDPOINT + "/autonomouscorporation";

    String CENSUS_OFFICE_HIERARCHY_ENDPOINT = API_ENDPOINT + "/censusofficehierarchy";

    String POST_ENDPOINT = API_ENDPOINT + "/post";

    String CENSUS_DATA_ENTRY_ENDPOINT = API_ENDPOINT + "/censusdataentry";

    String OFFICE_TYPE_BY_ORG_TYPE = "/officetypebyorgtype";

    String POST_BY_CLASS_ID = "/postbyclassid";

    String GET_ORG_TYPE_BY_ID = "/getorgtypebyid";

    String REPORT = API_ENDPOINT + "/report";

    String DIVISIONAL_DEPUTY_COMM_OFFICE_ENDPOINT = API_ENDPOINT + "/divisionaldeputycommoffice";

    String CENSUS_OFFICE_ENDPOINT = API_ENDPOINT + "/censusoffice";

    String CENSUS_DATA_ENTRY_PROJECT_ENDPOINT = API_ENDPOINT + "/censusdataentryproject";

    String CENSUS_ORG_TYPE_ENDPOINT = API_ENDPOINT + "/censusorgtype";

    String CENSUS_ORGANIZATION_ENDPOINT = API_ENDPOINT + "/censusorganization";

    String FLAG_EDIT = "flagedit";

    String FLAG_EDIT_PATH_VAR = "/{" + FLAG_EDIT + "}";

}
