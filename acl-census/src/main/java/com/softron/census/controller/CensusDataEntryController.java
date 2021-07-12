/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.controller;

import static com.softron.census.constant.WebApiConstants.BAR_CHART_FOR_AUTONOMOUS_BODIES_AND_CORPORATION;
import static com.softron.census.constant.WebApiConstants.BAR_CHART_FOR_DEPARTMENT_AND_DIRECTORATE;
import static com.softron.census.constant.WebApiConstants.BAR_CHART_FOR_DIVISIONAL_COMM_AND_DEPUTY_COMM;
import static com.softron.census.constant.WebApiConstants.BAR_CHART_FOR_MANPOWER_OF_ALL_ORG;
import static com.softron.census.constant.WebApiConstants.BAR_CHART_FOR_MINISTRIES_AND_DIVISION;
import static com.softron.census.constant.WebApiConstants.CENSUS_DATA_ENTRY_ENDPOINT;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF_EXCEL_REPORT;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF_PDF_REPORT;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL_FOR_EXCEL;
import static com.softron.census.constant.WebApiConstants.CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL_FOR_PDF;
import static com.softron.census.constant.WebApiConstants.ENUM;
import static com.softron.census.constant.WebApiConstants.FILTER;
import static com.softron.census.constant.WebApiConstants.OFFICE_TYPE_BY_ORG_TYPE;
import static com.softron.census.constant.WebApiConstants.OFFICE_TYPE_ENUM;
import static com.softron.census.constant.WebApiConstants.POST_BY_CLASS_ID;
import static com.softron.census.constant.WebApiConstants.REPORT;
import static com.softron.census.constant.WebApiConstants.SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE;
import static com.softron.census.constant.WebApiConstants.SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE_EXCEL_REPORT;
import static com.softron.census.constant.WebApiConstants.SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE_PDF_REPORT;
import static com.softron.census.constant.WebApiConstants.URL_ENUM;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.BY_USER_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.softron.census.domain.BarChart;
import com.softron.census.domain.BarChartData;
import com.softron.census.domain.CensusSearchTO;
import com.softron.census.domain.ReportDataTO;
import com.softron.census.domain.ReportModelTO;
import com.softron.census.schema.constant.OfficeTypeEnum;
import com.softron.census.schema.constant.OrgTypeEnum;
import com.softron.census.schema.entity.CensusDataEntry;
import com.softron.census.schema.entity.CensusOrganization;
import com.softron.census.schema.entity.CensusPeriod;
import com.softron.census.schema.entity.Division;
import com.softron.census.service.CensusDataEntryService;
import com.softron.census.service.CensusPeriodService;
import com.softron.census.service.PostService;
import com.softron.common.utils.AuthUtil;
import com.softron.core.annotations.ApiController;
import com.softron.core.domain.RequestContextData;

/**
 *
 * @author Mozahid
 */
@ApiController
public class CensusDataEntryController {

    @Autowired
    CensusPeriodService censusPeriodService;
    
    @Autowired
    private CensusDataEntryService censusDataEntryService;
    
    @Autowired
    private PostService postService;
    
    @Resource(name = "requestContextData")
	private RequestContextData requestContextData;

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusDataEntry() {
        return ResponseEntity.ok(censusDataEntryService.getAllCensusDataEntry());
    }
    
    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + BY_USER_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusDataEntryByUserOrg() {
    	Long moduleId = requestContextData.getModuleId().orElse(null);
        return ResponseEntity.ok(censusDataEntryService.getAllCensusDataEntryByUserOrg(moduleId, AuthUtil.getUserName()));
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveCensusDataEntry() {
        return ResponseEntity.ok(censusDataEntryService.getAllActive());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + ENUM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOrgType() {
        return ResponseEntity.ok(censusDataEntryService.getAllOrgType());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + POST_BY_CLASS_ID, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getPostByClassId(@RequestParam("id") String id) {
        return ResponseEntity.ok(postService.getPostByClassId(Long.valueOf(id)));
    }

    @GetMapping(value = REPORT + URL_ENUM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllUrl() {
        return ResponseEntity.ok(censusDataEntryService.getAllUrl());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + OFFICE_TYPE_ENUM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficeType() {
        return ResponseEntity.ok(censusDataEntryService.getAllOfficeType());
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + OFFICE_TYPE_BY_ORG_TYPE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOfficeTypeByOrgType(@PathVariable(ID) String name) {
        return ResponseEntity.ok(OfficeTypeEnum.getAllOfficeTypeByName(name));
    }

    @GetMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusDataEntryById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(censusDataEntryService.getCensusDataEntryById(id));
    }

     @PostMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + FILTER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllCensusDataEntryFilter(@RequestBody CensusSearchTO censusSearchTO) {
        return ResponseEntity.ok(censusDataEntryService.getAllCensusDataEntryFilter(censusSearchTO));
    }

    @DeleteMapping(value = CENSUS_DATA_ENTRY_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> deleteCensusDataEntry(@PathVariable(ID) Long id) {
        try {
            censusDataEntryService.delete(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("{\"message\":\"Record cant be delete.\"}");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = CENSUS_DATA_ENTRY_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveCensusDataEntry(@RequestBody CensusDataEntry censusDataEntry) {
        censusDataEntryService.save(censusDataEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = CENSUS_DATA_ENTRY_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateCensusDataEntry(@RequestBody CensusDataEntry CensusDataEntry) {
        censusDataEntryService.save(CensusDataEntry);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = REPORT + BAR_CHART_FOR_MANPOWER_OF_ALL_ORG, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getBarChart(@RequestParam("censusPeriodId") Long censusPeriodId) {
        BarChart barChart = new BarChart();
        ReportModelTO modelTO = new ReportModelTO();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        List<Object[]> reportTOs = censusDataEntryService.getSummaryOfManpower(censusPeriod.getCensusPeriod());
        List<String> list = new ArrayList<>();
        List<String> listNumber1 = new ArrayList<>();
        List<String> listNumber2 = new ArrayList<>();
        List<String> listNumber3 = new ArrayList<>();
        if (reportTOs != null && !reportTOs.isEmpty()) {
            for (Object[] reportObject : reportTOs) {
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (rowObject != null) {

                        if (i == 0) {
                            list.add(rowObject.toString());
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        if (reportTOs != null && !reportTOs.isEmpty()) {
            for (Object[] reportObject : reportTOs) {
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {

                        if (i == 1) {
                            listNumber1.add(rowObject.toString());
                        } else if (i == 2) {
                            listNumber2.add(rowObject.toString());
                        } else if (i == 3) {
                            listNumber3.add(rowObject.toString());
                        }
                        rowObject.toString();

                    } else {

                        if (i == 1) {
                            listNumber1.add("0");
                        } else if (i == 2) {
                            listNumber2.add("0");
                        } else if (i == 3) {
                            listNumber3.add("0");
                        }

                    }
                    i++;
                }

            }
        }

        List<BarChartData> barChartDatas = new ArrayList<>();
        BarChartData barChartData = new BarChartData();
        barChartData.setBackgroundColor("#26c6da");
        barChartData.setBorderColor("#26c6da");
        barChartData.setBorderWidth(1);
        barChartData.setData(listNumber1);

        barChartData.setLabel("Sanctioned");
        barChartDatas.add(barChartData);

        BarChartData barChartData1 = new BarChartData();
        barChartData1.setBackgroundColor("#ed850f");
        barChartData1.setBorderColor("#ed850f");
        barChartData.setBorderWidth(1);
        barChartData1.setData(listNumber2);

        barChartData1.setLabel("Existing");
        barChartDatas.add(barChartData1);

        BarChartData barChartData2 = new BarChartData();
        barChartData2.setBackgroundColor("#2ff523");
        barChartData2.setBorderColor("#2ff523");
        barChartData.setBorderWidth(1);
        barChartData2.setData(listNumber3);

        barChartData2.setLabel("Vacant");
        barChartDatas.add(barChartData2);

        barChart.setDatasets(barChartDatas);
        barChart.setLabels(list);
        modelTO.setIsChart(true);
        modelTO.setBarChart(barChart);
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + BAR_CHART_FOR_MINISTRIES_AND_DIVISION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getBarChartForMinistriesAndDivision(@RequestParam("censusPeriodId") Long censusPeriodId) {
        BarChart barChart = new BarChart();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ReportModelTO modelTO = new ReportModelTO();
        List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseMinistriesAndDivisionForChart(censusPeriod.getCensusPeriod());
        List<String> list = new ArrayList<>();
        List<String> listNumber1 = new ArrayList<>();
        List<String> listNumber2 = new ArrayList<>();
        List<String> listNumber3 = new ArrayList<>();
        if (censusPeriod != null) {
            list.add(censusPeriod.getCensusPeriod());

        }

        if (objects != null && !objects.isEmpty()) {
            for (Object[] reportObject : objects) {
                int i = 0;
                for (Object rowObject : reportObject) {

                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {

                        if (i == 14) {
                            listNumber1.add(rowObject.toString());
                        } else if (i == 15) {
                            listNumber2.add(rowObject.toString());
                        } else if (i == 16) {
                            listNumber3.add(rowObject.toString());
                        }
                        rowObject.toString();

                    } else {

                        if (i == 14) {
                            listNumber1.add("0");
                        } else if (i == 15) {
                            listNumber2.add("0");
                        } else if (i == 16) {
                            listNumber3.add("0");
                        }

                    }
                    i++;
                }

            }
        }
        List<BarChartData> barChartDatas = new ArrayList<>();
        BarChartData barChartData = new BarChartData();
        barChartData.setBackgroundColor("#26c6da");
        barChartData.setBorderColor("#26c6da");
        barChartData.setBorderWidth(1);
        barChartData.setData(listNumber1);
        barChartData.setLabel("Sanctioned");
        barChartDatas.add(barChartData);

        BarChartData barChartData1 = new BarChartData();
        barChartData1.setBackgroundColor("#ed850f");
        barChartData1.setBorderColor("#ed850f");
        barChartData.setBorderWidth(1);
        barChartData1.setData(listNumber2);
        barChartData1.setLabel("Existing");
        barChartDatas.add(barChartData1);

        BarChartData barChartData2 = new BarChartData();
        barChartData2.setBackgroundColor(" #2ff523 ");
        barChartData2.setBorderColor("# #2ff523 ");
        barChartData.setBorderWidth(1);
        barChartData2.setData(listNumber3);
        barChartData2.setLabel("Vacant");
        barChartDatas.add(barChartData2);

        barChart.setDatasets(barChartDatas);
        barChart.setLabels(list);
        modelTO.setIsChart(true);
        modelTO.setBarChart(barChart);
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + BAR_CHART_FOR_DEPARTMENT_AND_DIRECTORATE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getBarChartForDepartmentAndDirectorate(@RequestParam("censusPeriodId") Long censusPeriodId) {
        BarChart barChart = new BarChart();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseDepartmentAndDirectorateForChart(censusPeriod.getCensusPeriod());
        ReportModelTO modelTO = new ReportModelTO();
        List<Object[]> mainData = new ArrayList<>();
        List<String> list = new ArrayList<>();
        List<String> listNumber1 = new ArrayList<>();
        List<String> listNumber2 = new ArrayList<>();
        List<String> listNumber3 = new ArrayList<>();

        if (censusPeriod != null) {
            list.add(censusPeriod.getCensusPeriod());
        }

        if (objects != null && !objects.isEmpty()) {
            for (Object[] reportObject : objects) {
                int i = 0;
                for (Object rowObject : reportObject) {

                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {

                        if (i == 15) {
                            listNumber1.add(rowObject.toString());
                        } else if (i == 16) {
                            listNumber2.add(rowObject.toString());
                        } else if (i == 17) {
                            listNumber3.add(rowObject.toString());
                        }
                        rowObject.toString();

                    } else {

                        if (i == 15) {
                            listNumber1.add("0");
                        } else if (i == 16) {
                            listNumber2.add("0");
                        } else if (i == 17) {
                            listNumber3.add("0");
                        }

                    }
                    i++;
                }

            }
        }

        List<BarChartData> barChartDatas = new ArrayList<>();
        BarChartData barChartData = new BarChartData();
        barChartData.setBackgroundColor("#26c6da");
        barChartData.setBorderColor("#26c6da");
        barChartData.setBorderWidth(1);
        barChartData.setData(listNumber1);
        barChartData.setLabel("Sanctioned");
        barChartDatas.add(barChartData);

        BarChartData barChartData1 = new BarChartData();
        barChartData1.setBackgroundColor("#ed850f");
        barChartData1.setBorderColor("#ed850f");
        barChartData.setBorderWidth(1);
        barChartData1.setData(listNumber2);
        barChartData1.setLabel("Existing");
        barChartDatas.add(barChartData1);

        BarChartData barChartData2 = new BarChartData();
        barChartData2.setBackgroundColor("#2ff523");
        barChartData2.setBorderColor("#2ff523 ");
        barChartData.setBorderWidth(1);
        barChartData2.setData(listNumber3);
        barChartData2.setLabel("Vacant");
        barChartDatas.add(barChartData2);

        barChart.setDatasets(barChartDatas);
        barChart.setLabels(list);
        modelTO.setIsChart(true);
        modelTO.setBarChart(barChart);
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + BAR_CHART_FOR_AUTONOMOUS_BODIES_AND_CORPORATION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getBarChartForAutonomousBodiesAndCorporation(@RequestParam("censusPeriodId") Long censusPeriodId) {
        BarChart barChart = new BarChart();
        ReportModelTO modelTO = new ReportModelTO();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseAutonomousBodiesAndCorporationForChart(censusPeriod.getCensusPeriod());
        List<Object[]> mainData = new ArrayList<>();
        List<String> list = new ArrayList<>();
        List<String> listNumber1 = new ArrayList<>();
        List<String> listNumber2 = new ArrayList<>();
        List<String> listNumber3 = new ArrayList<>();
        if (censusPeriod != null) {
            list.add(censusPeriod.getCensusPeriod());
        }

        if (objects != null && !objects.isEmpty()) {
            for (Object[] reportObject : objects) {
                int i = 0;
                for (Object rowObject : reportObject) {

                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {

                        if (i == 15) {
                            listNumber1.add(rowObject.toString());
                        } else if (i == 16) {
                            listNumber2.add(rowObject.toString());
                        } else if (i == 17) {
                            listNumber3.add(rowObject.toString());
                        }
                        rowObject.toString();

                    } else {

                        if (i == 15) {
                            listNumber1.add("0");
                        } else if (i == 16) {
                            listNumber2.add("0");
                        } else if (i == 17) {
                            listNumber3.add("0");
                        }

                    }
                    i++;
                }

            }
        }

        List<BarChartData> barChartDatas = new ArrayList<>();
        BarChartData barChartData = new BarChartData();
        barChartData.setBackgroundColor("#26c6da");
        barChartData.setBorderColor("#26c6da");
        barChartData.setBorderWidth(1);
        barChartData.setData(listNumber1);
        barChartData.setLabel("Sanctioned");
        barChartDatas.add(barChartData);

        BarChartData barChartData1 = new BarChartData();
        barChartData1.setBackgroundColor("#ed850f");
        barChartData1.setBorderColor("#ed850f");
        barChartData.setBorderWidth(1);
        barChartData1.setData(listNumber2);
        barChartData1.setLabel("Existing");
        barChartDatas.add(barChartData1);

        BarChartData barChartData2 = new BarChartData();
        barChartData2.setBackgroundColor("#2ff523");
        barChartData2.setBorderColor("#2ff523");
        barChartData.setBorderWidth(1);
        barChartData2.setData(listNumber3);
        barChartData2.setLabel("Vacant");
        barChartDatas.add(barChartData2);

        barChart.setDatasets(barChartDatas);
        barChart.setLabels(list);
        modelTO.setIsChart(true);
        modelTO.setBarChart(barChart);
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + BAR_CHART_FOR_DIVISIONAL_COMM_AND_DEPUTY_COMM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getBarChartForDivisionalCommAndDeputyComm(@RequestParam("censusPeriodId") Long censusPeriodId) {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        BarChart barChart = new BarChart();
        ReportModelTO modelTO = new ReportModelTO();
        List<Object[]> mainData = new ArrayList<>();
        List<String> list = new ArrayList<>();
        List<String> listNumber1 = new ArrayList<>();
        List<String> listNumber2 = new ArrayList<>();
        List<String> listNumber3 = new ArrayList<>();
        List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseDivisionalCommAndDeputyCommForChart(censusPeriod.getCensusPeriod());
        if (censusPeriod != null) {
            list.add(censusPeriod.getCensusPeriod());
        }

        if (objects != null && !objects.isEmpty()) {
            for (Object[] reportObject : objects) {
                int i = 0;
                for (Object rowObject : reportObject) {

                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {

                        if (i == 15) {
                            listNumber1.add(rowObject.toString());
                        } else if (i == 16) {
                            listNumber2.add(rowObject.toString());
                        } else if (i == 17) {
                            listNumber3.add(rowObject.toString());
                        }
                        rowObject.toString();

                    } else {
                        if (i == 15) {
                            listNumber1.add("0");
                        } else if (i == 16) {
                            listNumber2.add("0");
                        } else if (i == 17) {
                            listNumber3.add("0");
                        }

                    }
                    i++;
                }

            }
        }

        List<BarChartData> barChartDatas = new ArrayList<>();
        BarChartData barChartData = new BarChartData();
        barChartData.setBackgroundColor("#26c6da");
        barChartData.setBorderColor("#26c6da");
        barChartData.setBorderWidth(1);
        barChartData.setData(listNumber1);
        barChartData.setLabel("Sanctioned");
        barChartDatas.add(barChartData);

        BarChartData barChartData1 = new BarChartData();
        barChartData1.setBackgroundColor("#ed850f");
        barChartData1.setBorderColor("#ed850f");
        barChartData.setBorderWidth(1);
        barChartData1.setData(listNumber2);
        barChartData1.setLabel("Existing");
        barChartDatas.add(barChartData1);

        BarChartData barChartData2 = new BarChartData();
        barChartData2.setBackgroundColor("#2ff523");
        barChartData2.setBorderColor("#2ff523");
        barChartData.setBorderWidth(1);
        barChartData2.setData(listNumber3);
        barChartData2.setLabel("Vacant");
        barChartDatas.add(barChartData2);

        barChart.setDatasets(barChartDatas);
        barChart.setLabels(list);
        modelTO.setIsChart(true);
        modelTO.setBarChart(barChart);
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getCensusReportData(@RequestParam("censusPeriodId") Long censusPeriodId) {

        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> yearList = censusDataEntryService.getCivilOfficerAndStaffDataByYear(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfCivilOfficerAndStaffByYear(censusPeriod.getCensusPeriod());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Class").append("</th>");
        for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
            stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(orgType.getValue()).append("</th>");
        }
        stringBuilder.append("<th rowspan='2'  colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }
        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        if (yearList != null && !yearList.isEmpty()) {
            for (Object[] reportObject : yearList) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        if (totaList != null && !totaList.isEmpty()) {
            for (Object[] reportObject : totaList) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getSummaryOfManpowerForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getSummaryOfManpower(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfSummaryOfManpower(censusPeriod.getCensusPeriod());
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Year").append("</th>");
        for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
            stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(orgType.getValue()).append("</th>");
        }
        stringBuilder.append("<th rowspan='2'  colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }
        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        if (reportTOs != null && !reportTOs.isEmpty()) {
            for (Object[] reportObject : reportTOs) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        if (totaList != null && !totaList.isEmpty()) {
            for (Object[] reportObject : totaList) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorateForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseStatisticsOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseStatisticsOfDepartmentAndDirectorate(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseStatisticsOfAutonomousBodiesCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseStatisticsOfAutonomousBodiesCorporation(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseMinistriesAndDivision11ByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseMinistriesAndDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {

                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfOfficersAndStaffOfDivisionalCommAndDeputyCommForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Division> divisions = censusDataEntryService.getAllDivision();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;' >").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (Division division : divisions) {
            if (divisions != null && !divisions.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseDivisionalCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = division.getName();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfDivisionalCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Male").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Female").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfMinistryDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfMinistryDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.78000(Fixed)").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.66000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.56500").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.50000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.43000").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11=''  style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClass1OfficerOfDepartmentAndDirectorateByPayscaleForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.78000(Fixed)").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.66000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.56500").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.50000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.43000").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11=''  style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfDepartmentAndDirectorateByPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfDepartmentAndDirectorateByPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClass1OfficerOfMinistriesDivisionByPayscaleForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11=''>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11=''>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.78000(Fixed)").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.66000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.56500").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.50000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.43000").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfMinistriesDivisionByPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfMinistriesDivisionByPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesAndCorporationForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Male").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Female").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfAutomousAndCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfAutomousAndCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }

        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDivisionalCommissionerAndDeputyCommissionerForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<Division> divisions = censusDataEntryService.getAllDivision();
        stringBuilder.append("<table class='table_report' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Male").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Female").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (Division d : divisions) {
            if (divisions != null && !divisions.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfDivCommDeputyCommByParentNameByParentName(d.getName(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = d.getName();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleDivCommDeputyCommByParentName(d.getName(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClass1OfficerOfDivCommDepCommByPayscaleForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<Division> divisions = censusDataEntryService.getAllDivision();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Divisional And Deputy Commissioner").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.78000(Fixed)").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.66000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.56500").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.50000").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("TK.43000").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Vacant").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (Division d : divisions) {
            if (divisions != null && !divisions.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfDivCommDepCommByPayscaleByName(d.getName(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = d.getName();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfDivCommDepCommByPayscaleByParentName(d.getName(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDepartmentAndDirectorateForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        stringBuilder.append("<table class='table_report' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministries And Divisions").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-I").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-II").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-III").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("CLASS-IV").append("</th>");
        stringBuilder.append("<th rowspan='2' colspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");

        stringBuilder.append("</tr>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Male").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Female").append("</td>");
            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
        }

        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                        }
                    }
                    i++;
                }
                stringBuilder.append("</tr>");
            }
        }
        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfFemaleOfficersAndStaffDataForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getClassWiseStaticsOfFemaleOfficersAndStaff11Data(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfClassWiseStaticsOfFemaleOfficersAndStaff11Data(censusPeriod.getCensusPeriod());

        stringBuilder.append("<table class='table_report' width='100%' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='3' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Class").append("</th>");
        for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
            stringBuilder.append("<th  _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(orgType.getValue()).append("</th>");
        }
        stringBuilder.append("<th  _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        if (reportTOs != null && !reportTOs.isEmpty()) {
            for (Object[] reportObject : reportTOs) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        if (totaList != null && !totaList.isEmpty()) {
            for (Object[] reportObject : totaList) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</td>");
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getClassWiseStaticsOfProjectPersonnelForDisplay(@RequestParam("censusPeriodId") Long censusPeriodId) {
        StringBuilder stringBuilder = new StringBuilder();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getClassWiseStaticsOfProjectPersonnel11Data(censusPeriod.getCensusPeriod());
        stringBuilder.append("<table class='table_report' width='100%' _ngcontent-c11=''>");

        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th rowspan='2' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Sr No.").append("</th>");
        stringBuilder.append("<th rowspan='2' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Ministry And Division").append("</th>");
        stringBuilder.append("<th rowspan='2' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("No Of Project").append("</th>");
        stringBuilder.append("<th colspan='4'_ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Number of Personnel Employed").append("</th>");
        stringBuilder.append("<th rowspan='2' _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Total").append("</th>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<th  _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Class I").append("</th>");
        stringBuilder.append("<th  _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Class II").append("</th>");
        stringBuilder.append("<th  _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Class III").append("</th>");
        stringBuilder.append("<th  _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("Class IV").append("</th>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'></tr>");
        if (reportTOs != null && !reportTOs.isEmpty()) {
            int increment = 0;
            for (Object[] reportObject : reportTOs) {
                stringBuilder.append("<tr _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>");
                if (reportObject != null && !reportObject.toString().equalsIgnoreCase("null")) {
                    stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(++increment).append("</td>");
                }
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append(rowObject.toString()).append("</td>");
                    } else {
                        stringBuilder.append("<td _ngcontent-c11='' style='border: 1px solid black; padding: 10px;'>").append("0").append("</td>");
                    }
                }
                stringBuilder.append("</tr>");
            }
        }

        ReportModelTO modelTO = new ReportModelTO();
        modelTO.setIsChart(false);
        modelTO.setHtml(stringBuilder.toString().replace("null", "0"));
        return ResponseEntity.ok(modelTO);
    }

    @GetMapping(value = REPORT + CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF_EXCEL_REPORT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportCivilOfferAndStaffExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getCivilOfficerAndStaffDataByYear(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfCivilOfficerAndStaffByYear(censusPeriod.getCensusPeriod());

        if (reportTOs != null && !reportTOs.isEmpty()) {
            if (totaList != null && !totaList.isEmpty()) {
                Sheet sheet = wb.createSheet();
                createHeaderRow(wb, sheet, censusPeriod.getCensusPeriod());
                createWorkSheetForCivilOfficerAndStaff(reportTOs, totaList, sheet, wb);
                wb.write(baos);

            }
        }

        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRow(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("3.CLASS-WISE BREAKDOWN OF CIVIL OFFICERS AND STAFF( " + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(6);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        row1.setRowStyle(style1);
        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        row1.setRowStyle(style1);
        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        row1.setRowStyle(style1);
        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Departments and Directorates");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        row1.setRowStyle(style1);
        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        row1.setRowStyle(style1);
        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Divisional and DeputyCommOffice");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        row1.setRowStyle(style1);
        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        row1.setRowStyle(style1);
        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        row1.setRowStyle(style1);
        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Autonomous Bodies and Corporation");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        row1.setRowStyle(style1);
        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        row1.setRowStyle(style1);
        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        row1.setRowStyle(style1);
        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Commission");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        row1.setRowStyle(style1);
        cell = row1.createCell(15);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(15);

        row1.setRowStyle(style1);
        cell = row1.createCell(16);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(16);

        row1.setRowStyle(style1);
        cell = row1.createCell(17);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(17);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        Row row2 = sheet.createRow(rowNum++);
        int cellNumber = 0;
        for (int i = 1; i <= 6; i++) {
            row2.setRowStyle(style2);
            cell = row2.createCell(++cellNumber);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(cellNumber);

            row2.setRowStyle(style2);
            cell = row2.createCell(++cellNumber);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(cellNumber);

            row2.setRowStyle(style2);
            cell = row2.createCell(++cellNumber);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(cellNumber);
        }
    }

    public void createWorkSheetForCivilOfficerAndStaff(List<Object[]> reportTOs, List<Object[]> totaList, Sheet sheet, Workbook wb) {
        int rowNum = 2;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        if (reportTOs != null && !reportTOs.isEmpty()) {

            for (Object[] reportObject : reportTOs) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                for (Object rowObject : reportObject) {
                    Cell cell = row.createCell(cellNo++);
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rowObject.toString());
                        cell.setCellStyle(style);
                    } else {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("0");
                        cell.setCellStyle(style);
                    }
                }

            }
        }

        if (totaList != null && !totaList.isEmpty()) {
            int cellNo = 0;
            Row row = sheet.createRow(++rowNum);
            Cell cell = row.createCell(cellNo++);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            for (Object[] reportObject : totaList) {
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell = row.createCell(cellNo++);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rowObject.toString());
                        sheet.autoSizeColumn(rowNum);
                    } else {
                        cell = row.createCell(cellNo++);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("0");
                        sheet.autoSizeColumn(rowNum);

                    }
                }
            }
        }
    }

    @GetMapping(value = REPORT + SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE_EXCEL_REPORT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportSummaryOfManpowerForExcel(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Object[]> reportTOs = censusDataEntryService.getSummaryOfManpower(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfSummaryOfManpower(censusPeriod.getCensusPeriod());

        if (reportTOs != null && !reportTOs.isEmpty()) {
            if (totaList != null && !totaList.isEmpty()) {
                Sheet sheet = wb.createSheet();
                createHeaderRowforSummaryOfManpower(wb, sheet, censusPeriod.getCensusPeriod());
                createWorkSheetForSummaryOfManpower(reportTOs, totaList, sheet, wb);
                wb.write(baos);

            }
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforSummaryOfManpower(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("1.SUMMARY OF MANPOWER OF MINISTRIES AND DIVISIONS,DEPARTMENTS AND DIRECTORATES DIV. COM AND DY. COM OFFICES,AUTONOMOUS BODIES AND CORPORATION (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(6);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Year");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        row1.setRowStyle(style1);
        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        row1.setRowStyle(style1);
        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        row1.setRowStyle(style1);
        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Departments and Directorates");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        row1.setRowStyle(style1);
        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        row1.setRowStyle(style1);
        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Divisional and DeputyCommOffice");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        row1.setRowStyle(style1);
        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        row1.setRowStyle(style1);
        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        row1.setRowStyle(style1);
        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Autonomous Bodies and Corporation");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        row1.setRowStyle(style1);
        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        row1.setRowStyle(style1);
        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        row1.setRowStyle(style1);
        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Commission");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        row1.setRowStyle(style1);
        cell = row1.createCell(15);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(15);

        row1.setRowStyle(style1);
        cell = row1.createCell(16);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(16);

        row1.setRowStyle(style1);
        cell = row1.createCell(17);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(17);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        Row row2 = sheet.createRow(rowNum++);
        int cellNumber = 0;
        for (int i = 1; i <= 6; i++) {
            row2.setRowStyle(style2);
            cell = row2.createCell(++cellNumber);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(cellNumber);

            row2.setRowStyle(style2);
            cell = row2.createCell(++cellNumber);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(cellNumber);

            row2.setRowStyle(style2);
            cell = row2.createCell(++cellNumber);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(cellNumber);
        }
    }

    public void createWorkSheetForSummaryOfManpower(List<Object[]> reportTOs, List<Object[]> totaList, Sheet sheet, Workbook wb) {
        int rowNum = 2;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        if (reportTOs != null && !reportTOs.isEmpty()) {

            for (Object[] reportObject : reportTOs) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                for (Object rowObject : reportObject) {
                    Cell cell = row.createCell(cellNo++);
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rowObject.toString());
                        cell.setCellStyle(style);
                    } else {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("0");
                        cell.setCellStyle(style);
                    }
                }

            }
        }

        if (totaList != null && !totaList.isEmpty()) {
            int cellNo = 0;
            Row row = sheet.createRow(++rowNum);
            Cell cell = row.createCell(cellNo++);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            for (Object[] reportObject : totaList) {
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell = row.createCell(cellNo++);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rowObject.toString());
                        sheet.autoSizeColumn(rowNum);
                    } else {
                        cell = row.createCell(cellNo++);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("0");
                        sheet.autoSizeColumn(rowNum);

                    }
                }
            }
        }
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfFemaleOfficersAndStaffDataForDisplayExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getClassWiseStaticsOfFemaleOfficersAndStaff11Data(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfClassWiseStaticsOfFemaleOfficersAndStaff11Data(censusPeriod.getCensusPeriod());
        if (reportTOs != null && !reportTOs.isEmpty()) {
            if (totaList != null && !totaList.isEmpty()) {
                Sheet sheet = wb.createSheet();
                createHeaderRowforClassWiseStaticsOfFemaleOfficersAndStaff(wb, sheet, censusPeriod.getCensusPeriod());
                createWorkSheetForClassWiseStaticsOfFemaleOfficersAndStaff(reportTOs, totaList, sheet, wb);
                wb.write(baos);

            }
        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClassWiseStaticsOfFemaleOfficersAndStaff(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("12.CLASS-WISE STATISTICS OF FEMALE OFFICER AND STAFF (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        row1.setRowStyle(style1);
        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Departments and Directorates");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        row1.setRowStyle(style1);
        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Divisional and DeputyCommOffice");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        row1.setRowStyle(style1);
        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Autonomous Bodies and Corporation");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Commission");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        row1.setRowStyle(style1);
        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

    }

    public void createWorkSheetForClassWiseStaticsOfFemaleOfficersAndStaff(List<Object[]> reportTOs, List<Object[]> totalList, Sheet sheet, Workbook wb) {
        int rowNum = 2;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        if (reportTOs != null && !reportTOs.isEmpty()) {

            for (Object[] reportObject : reportTOs) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                for (Object rowObject : reportObject) {
                    Cell cell = row.createCell(cellNo++);
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rowObject.toString());
                        cell.setCellStyle(style);
                    } else {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("0");
                        cell.setCellStyle(style);
                    }
                }

            }
        }

        if (totalList != null && !totalList.isEmpty()) {
            int cellNo = 0;
            Row row = sheet.createRow(++rowNum);
            Cell cell = row.createCell(cellNo++);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            for (Object[] reportObject : totalList) {
                for (Object rowObject : reportObject) {
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell = row.createCell(cellNo++);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rowObject.toString());
                        cell.setCellStyle(style);
                    } else {
                        cell = row.createCell(cellNo++);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("0");
                        cell.setCellStyle(style);

                    }
                }
            }
        }
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfProjectPersonnelDataExcelReport() throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Object[]> reportTOs = censusDataEntryService.getClassWiseStaticsOfProjectPersonnelData();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowforClassWiseStaticsOfProjectPersonnelData(wb, sheet);
            createWorkSheetForClassWiseStaticsOfProjectPersonnelData(reportTOs, sheet, wb);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClassWiseStaticsOfProjectPersonnelData(Workbook wb, Sheet sheet) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("17.CLASS-WISE STATISTICS OF PROJECT PERSONNEL OF THE GOVERNMENT OF BANGLADESH");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Sr No.");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        row1.setRowStyle(style1);
        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("No Of Projects");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        row1.setRowStyle(style1);
        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Number Of Personnel Employed");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        row1.setRowStyle(style1);
        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        Row row8 = sheet.createRow(rowNum++);
        row8.setRowStyle(style1);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row8.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        cell = row8.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        row8.setRowStyle(style1);
        cell = row8.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        row8.setRowStyle(style1);
        cell = row8.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row8.setRowStyle(style1);
        cell = row8.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        row8.setRowStyle(style1);
        cell = row8.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

    }

    public void createWorkSheetForClassWiseStaticsOfProjectPersonnelData(List<Object[]> reportTOs, Sheet sheet, Workbook wb) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        if (reportTOs != null && !reportTOs.isEmpty()) {

            for (Object[] reportObject : reportTOs) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                Cell cell = row.createCell(cellNo++);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(++increament);
                for (Object rowObject : reportObject) {
                    Cell cell1 = row.createCell(cellNo++);
                    cell1.setCellType(CellType.STRING);
                    if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                        cell1.setCellType(CellType.STRING);
                        cell1.setCellValue(rowObject.toString());
                        cell.setCellStyle(style);
                    } else {
                        cell1.setCellType(CellType.STRING);
                        cell1.setCellValue("0");
                        cell.setCellStyle(style);
                    }
                }

            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorateForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorateData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorateData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorateData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("5.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF DEPARTMENTS AND DIRECTORATES (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);
        }

    }

    public void createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorateData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseStatisticsOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseStatisticsOfDepartmentAndDirectorate(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);
                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("4.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF MINISTRIES AND DIVISION (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseMinistriesAndDivision11ByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseMinistriesAndDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfOfficersAndStaffOfDivisionCommAndDeputyCommForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Division> divisions = censusDataEntryService.getAllDivision();

        if (divisions != null && !divisions.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfDivisionCommAndDeputyCommData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfDivisionCommAndDeputyCommData(mainData, divisions, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfDivisionCommAndDeputyCommData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("6.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF DIV. COMM AND DY. COM OFFICES (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(rowNum);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfDivisionCommAndDeputyCommData(List<Object[]> mainData, List<Division> divisions, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (Division division : divisions) {
            if (divisions != null && !divisions.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseDivisionalCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = division.getName();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfDivisionalCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClass1OfficerOfDivCommDepCommByPayscaleForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Division> divisions = censusDataEntryService.getAllDivision();

        if (divisions != null && !divisions.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowforClass1OfficerOfDivCommDepCommByPayscaleData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClass1OfficerOfDivCommDepCommByPayscaleData(mainData, divisions, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClass1OfficerOfDivCommDepCommByPayscaleData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("10.STATISTICS OF CLASS-I OFFICERS OF DIV. COMM AND DY. COM OFFICES BY NATIONAL PAY SCALE (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Divisional And Deputy Commissioner");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.78000(Fixed)");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.66000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.56500");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.50000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.43000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClass1OfficerOfDivCommDepCommByPayscaleData(List<Object[]> mainData, List<Division> divisions, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (Division division : divisions) {
            if (divisions != null && !divisions.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfDivCommDepCommByPayscaleByName(division.getName(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = division.getName();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfDivCommDepCommByPayscaleByParentName(division.getName(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowforClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("7.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF AUTONOMOUS BODIES AND CORPORATIONS (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClassWiseStatisticsOfAutonomousBodiesCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseStatisticsOfAutonomousBodiesCorporation(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("13.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF OF MINISTRIES AND DIVISION (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Male");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Female");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfMinistryDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfMinistryDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);
                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporationForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporationData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporationData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporationData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("16.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF AUTONOMOUS BODIES CORPORATION (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Male");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Female");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporationData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfAutomousAndCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfAutomousAndCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClass1OfficerOfAutonomousBodiesAndCorporationPayscaleForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClass1OfficerOfAutonomousBodiesAndCorporationPayscaleData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClass1OfficerOfAutonomousBodiesAndCorporationPayscaleData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClass1OfficerOfAutonomousBodiesAndCorporationPayscaleData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("11.STATISTICS OF CLASS-I OFFICERS OF AUTONOMOUS BODIES AND CORPORATIONS BY NATIONAL PAY SCALE (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.78000(Fixed)");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.66000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.56500");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.50000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(rowNum);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.43000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClass1OfficerOfAutonomousBodiesAndCorporationPayscaleData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportcreateHeaderRowForClass1OfficerOfMinistriesDivisionByPayscaleByNameDataForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClass1OfficerOfMinistriesDivisionByPayscaleByNameData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClass1OfficerOfMinistriesDivisionByPayscaleByNameData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClass1OfficerOfMinistriesDivisionByPayscaleByNameData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("8.STATISTICS OF CLASS-I OFFICERS OF THE MINISTRIES AND DIVISIONS BY NATIONAL PAY SCALE (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.78000(Fixed)");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.66000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.56500");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.50000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(rowNum);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.43000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClass1OfficerOfMinistriesDivisionByPayscaleByNameData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfMinistriesDivisionByPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfMinistriesDivisionByPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClass1OfficerOfDepartmentAndDirectorateByPayscaleForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClass1OfficerOfDepartmentAndDirectorateByPayscaleData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClass1OfficerOfDepartmentAndDirectorateByPayscaleData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClass1OfficerOfDepartmentAndDirectorateByPayscaleData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("9.STATISTICS OF CLASS-I OFFICERS OF THE DEPARTMENT AND DIRECTORATES BY NATIONAL PAY SCALE (" + censusPeriod + ")");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.78000(Fixed)");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.66000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.56500");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.50000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("TK.43000");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Sanction");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Exist");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Vacant");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClass1OfficerOfDepartmentAndDirectorateByPayscaleData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getClass1OfficerOfDepartmentAndDirectorateByPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfDepartmentAndDirectorateByPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorateForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();

        if (reportTOs != null && !reportTOs.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorateData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorateData(mainData, reportTOs, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorateData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("14.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF OF DEPARTMENT AND DIRECTORATES (" + censusPeriod + ") ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Male");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Female");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);
        }

    }

    public void createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorateData(List<Object[]> mainData, List<CensusOrganization> reportTOs, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (CensusOrganization reportTO : reportTOs) {
            if (reportTOs != null && !reportTOs.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = reportTO.getNameEng();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);
                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER_FOR_EXCEL, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> reportClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDivisionalCommAndDeputyCommForExcelReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws FileNotFoundException, IOException, InvalidFormatException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Division> divisions = censusDataEntryService.getAllDivision();

        if (divisions != null && !divisions.isEmpty()) {
            Sheet sheet = wb.createSheet();
            createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffofDivisionalCommAndDeputyCommData(wb, sheet, censusPeriod.getCensusPeriod());
            createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffofDivisionalCommAndDeputyCommData(mainData, divisions, sheet, wb, censusPeriod);
            wb.write(baos);

        } else {
            Sheet sheet = wb.createSheet();
            wb.write(baos);
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        reportDataTO.setByteArrays(baos.toByteArray());
        return ResponseEntity.ok(reportDataTO);

    }

    public void createHeaderRowForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffofDivisionalCommAndDeputyCommData(Workbook wb, Sheet sheet, String censusPeriod) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Font boldFont = wb.createFont();
        boldFont.setFontName("Arial");
        boldFont.setColor(IndexedColors.BLACK.getIndex());
        boldFont.setBold(true);
        boldFont.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldFont);
        style.setWrapText(true);

        row.setRowStyle(style);
        Cell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("15.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF DIV. COM & DY.COM OFFICES  (" + censusPeriod + ") ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(boldFont);
        style1.setWrapText(true);

        Row row1 = sheet.createRow(rowNum++);
        row1.setRowStyle(style1);
        cell = row1.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Ministries and Division");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        cell = row1.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(1);

        row1.setRowStyle(style1);
        cell = row1.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-I");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(2);

        cell = row1.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(3);

        cell = row1.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(4);

        row1.setRowStyle(style1);
        cell = row1.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-II");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(5);

        cell = row1.createCell(6);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(6);

        cell = row1.createCell(7);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(7);

        row1.setRowStyle(style1);
        cell = row1.createCell(8);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-III");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(8);

        cell = row1.createCell(9);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(9);

        cell = row1.createCell(10);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(10);

        cell = row1.createCell(11);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Class-IV");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(11);

        cell = row1.createCell(12);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(12);

        cell = row1.createCell(13);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(13);

        cell = row1.createCell(14);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Total");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(14);

        Row row8 = sheet.createRow(rowNum++);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setFont(boldFont);
        style2.setWrapText(true);

        row8.setRowStyle(style2);
        cell = row8.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(" ");
        cell.setCellStyle(style1);
        sheet.autoSizeColumn(0);

        for (int i = 1; i < 16; i++) {
            cell = row8.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Male");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Female");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

            cell = row8.createCell(++i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("Total");
            cell.setCellStyle(style2);
            sheet.autoSizeColumn(i);

        }

    }

    public void createWorkSheetForClassWiseStaticsOfMaleAndFemaleOfficerAndStaffofDivisionalCommAndDeputyCommData(List<Object[]> mainData, List<Division> divisions, Sheet sheet, Workbook wb, CensusPeriod censusPeriod) {
        int rowNum = 2;
        int increament = 0;
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        for (Division division : divisions) {
            if (divisions != null && !divisions.isEmpty()) {
                List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfDivCommDeputyCommByParentNameByParentName(division.getName(), censusPeriod.getCensusPeriod());
                if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                    Object[] obj = new Object[17];
                    obj[1] = division.getName();
                    for (int i = 2; i < 17; i++) {
                        obj[i] = " ";
                    }
                    lists.add(0, obj);
                    mainData.addAll(lists);
                }
            }
            List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleDivCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
            if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                for (Object[] obj : objects) {
                    obj[1] = "Total";
                }
                mainData.addAll(objects);
            }
        }

        if (mainData != null && !mainData.isEmpty()) {
            for (Object[] reportObject : mainData) {
                Row row = sheet.createRow(++rowNum);
                int cellNo = 0;
                int i = 0;
                for (Object rowObject : reportObject) {
                    if (i > 0) {
                        Cell cell = row.createCell(cellNo++);
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(rowObject.toString());
                            cell.setCellStyle(style);
                        } else {
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(0);
                            cell.setCellStyle(style);

                        }
                    }
                    i++;
                }
            }
        }

    }

    @GetMapping(value = REPORT + CLASS_WISE_BREAKDOWN_OF_CIVIL_OFFICER_AND_STAFF_PDF_REPORT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> censusCivilOfficerAndStaffPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getCivilOfficerAndStaffDataByYear(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfCivilOfficerAndStaffByYear(censusPeriod.getCensusPeriod());
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfForCivilOfficerAndStaff()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>");
            stringBuilder.append("3.CLASS-WISE BREAKDOWN OF CIVIL OFFICERS AND STAFF (" + censusPeriod.getCensusPeriod() + ")");
            stringBuilder.append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' style='width:180px;'>").append("Class").append("</th>");
            for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
                stringBuilder.append("<th rowspan='2' style='width:200px;' colspan='3' >").append(orgType.getValue()).append("</th>");
            }
            stringBuilder.append("<th rowspan='2' style='width:200px;' colspan='3' >").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr></tr>");
            stringBuilder.append("<tr>");
            for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
                stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Sanction").append("</td>");
                stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Exist").append("</td>");
                stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Vacant").append("</td>");
            }
            stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Vacant").append("</td>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr ></tr>");

            if (reportTOs != null && !reportTOs.isEmpty()) {
                for (Object[] reportObject : reportTOs) {
                    stringBuilder.append("<tr>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }

            if (totaList != null && !totaList.isEmpty()) {
                for (Object[] reportObject : totaList) {
                    stringBuilder.append("<tr>");
                    stringBuilder.append("<td>").append("Total").append("</td>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfForCivilOfficerAndStaff());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfForCivilOfficerAndStaff() throws FileNotFoundException, IOException {

        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + SUMMARY_OF_MANPOWER_OF_ALL_ORG_TYPE_PDF_REPORT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getSummaryOfManpowerForPdf(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getSummaryOfManpower(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfSummaryOfManpower(censusPeriod.getCensusPeriod());

        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfForSummaryOfManpower()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>");
            stringBuilder.append("1.SUMMARY OF MANPOWER OF MINISTRIES AND DIVISIONS,DEPARTMENTS AND DIRECTORATES DIV. COM AND DY. COM OFFICES,AUTONOMOUS BODIES AND CORPORATION (" + censusPeriod.getCensusPeriod() + ")");
            stringBuilder.append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' style='width:180px;'>").append("Year").append("</th>");
            for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
                stringBuilder.append("<th rowspan='2' style='width:200px;' colspan='3' >").append(orgType.getValue()).append("</th>");
            }
            stringBuilder.append("<th rowspan='2' style='width:200px;' colspan='3' >").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr></tr>");
            stringBuilder.append("<tr>");
            for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
                stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Sanction").append("</td>");
                stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Exist").append("</td>");
                stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Vacant").append("</td>");
            }
            stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Sanction").append("</td>");
            stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Exist").append("</td>");
            stringBuilder.append("<td   style='width:180px;padding:5px;'>").append("Vacant").append("</td>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr ></tr>");

            if (reportTOs != null && !reportTOs.isEmpty()) {
                for (Object[] reportObject : reportTOs) {
                    stringBuilder.append("<tr>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }

            if (totaList != null && !totaList.isEmpty()) {
                for (Object[] reportObject : totaList) {
                    stringBuilder.append("<tr>");
                    stringBuilder.append("<td>").append("Total").append("</td>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfForSummaryOfManpower());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfForSummaryOfManpower() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_FEMALE_OFFICERS_AND_STAFF_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfFemaleOfficersAndStaffPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> reportTOs = censusDataEntryService.getClassWiseStaticsOfFemaleOfficersAndStaff11Data(censusPeriod.getCensusPeriod());
        List<Object[]> totaList = censusDataEntryService.getTotalOfClassWiseStaticsOfFemaleOfficersAndStaff11Data(censusPeriod.getCensusPeriod());
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfForClassWiseStaticsOfFemaleOfficersAndStaff()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("12.CLASS-WISE STATISTICS OF FEMALE OFFICER AND STAFF (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th>").append("Class").append("</th>");
            for (OrgTypeEnum orgType : OrgTypeEnum.getAllOrgType()) {
                stringBuilder.append("<th>").append(orgType.getValue()).append("</th>");
            }
            stringBuilder.append("<th>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr ></tr>");

            if (reportTOs != null && !reportTOs.isEmpty()) {
                for (Object[] reportObject : reportTOs) {
                    stringBuilder.append("<tr>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }

            if (totaList != null && !totaList.isEmpty()) {
                for (Object[] reportObject : totaList) {
                    stringBuilder.append("<tr>");
                    stringBuilder.append("<td>").append("Total").append("</td>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfForClassWiseStaticsOfFemaleOfficersAndStaff());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfForClassWiseStaticsOfFemaleOfficersAndStaff() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_PROJECT_PERSONNEL_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfProjectPersonnelPdfReport() throws IOException {
        List<Object[]> reportTOs = censusDataEntryService.getClassWiseStaticsOfProjectPersonnelData();

        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfProjectPersonnel()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("17.CLASS-WISE STATISTICS OF PROJECT PERSONNEL OF THE GOVERNMENT OF BANGLADESH").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='2' >").append("Sr No.").append("</th>");
            stringBuilder.append("<th rowspan='2' >").append("Ministry And Division").append("</th>");
            stringBuilder.append("<th rowspan='2' >").append("No Of Project").append("</th>");
            stringBuilder.append("<th colspan='4'>").append("Number of Personnel Employed").append("</th>");
            stringBuilder.append("<th rowspan='2' >").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            stringBuilder.append("<th>").append("Class I").append("</th>");
            stringBuilder.append("<th>").append("Class II").append("</th>");
            stringBuilder.append("<th>").append("Class III").append("</th>");
            stringBuilder.append("<th>").append("Class IV").append("</th>");
            stringBuilder.append("</tr>");

            if (reportTOs != null && !reportTOs.isEmpty()) {
                int increament = 0;
                for (Object[] reportObject : reportTOs) {
                    stringBuilder.append("<tr>");
                    stringBuilder.append("<td>").append(++increament).append("</td>");
                    for (Object rowObject : reportObject) {
                        if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                            stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                        } else {
                            stringBuilder.append("<td>").append("0").append("</td>");
                        }
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfProjectPersonnel());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfProjectPersonnel() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DEPARTMENT_AND_DIRECTORATE_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectoratePdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorate()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("5.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF DEPARTMENTS AND DIRECTORATES (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClassWiseStatisticsOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseStatisticsOfDepartmentAndDirectorate(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorate());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfOfficersAndStaffOfDepartmentAndDirectorate() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_AUTONOMOUS_BODIES_AND_CORPORATION_PAYSCALE_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClass1OfficerOfAutonomousBodiesAndCorporationPayscalePdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClass1OfficerOfAutonomousBodiesAndCorporationPayscale()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("11.STATISTICS OF CLASS-I OFFICERS OF AUTONOMOUS BODIES AND CORPORATIONS BY NATIONAL PAY SCALE (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.78000(Fixed)").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.66000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.56500").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.50000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.43000").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClass1OfficerOfAutonomousBodiesAndCorporationPayscale());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClass1OfficerOfAutonomousBodiesAndCorporationPayscale() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_MINISTRIES_AND_DIVISION_BY_PAYSCALE_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClass1OfficerOfMinistriesDivisionByPayscalePdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClass1OfficerOfMinistriesDivisionByPayscale()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("8.STATISTICS OF CLASS-I OFFICERS OF THE MINISTRIES AND DIVISIONS BY NATIONAL PAY SCALE (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.78000(Fixed)").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.66000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.56500").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.50000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.43000").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClass1OfficerOfMinistriesDivisionByPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfMinistriesDivisionByPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClass1OfficerOfMinistriesDivisionByPayscale());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClass1OfficerOfMinistriesDivisionByPayscale() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_DEPARTMENT_AND_DIRECTORATE_BY_PAYSCALE_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClass1OfficerOfDepartmentAndDirectorateByPayscalePdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClass1OfficerOfDepartmentAndDirectorateByPayscale()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("9.STATISTICS OF CLASS-I OFFICERS OF THE DEPARTMENT AND DIRECTORATES BY NATIONAL PAY SCALE (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.78000(Fixed)").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.66000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.56500").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.50000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.43000").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClass1OfficerOfDepartmentAndDirectorateByPayscaleByName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfDepartmentAndDirectorateByPayscaleByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClass1OfficerOfDepartmentAndDirectorateByPayscale());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClass1OfficerOfDepartmentAndDirectorateByPayscale() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_MINISTRY_AND_DIVISION_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivisionPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivision()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("4.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF MINISTRIES AND DIVISION (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'>");
            stringBuilder.append("<tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClassWiseMinistriesAndDivision11ByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseMinistriesAndDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr _ngcontent-c11=''>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td _ngcontent-c11=''>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td _ngcontent-c11=''>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivision());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfOfficersAndStaffOfMinistryAndDivision() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_DIVISIONAL_COMM_AND_DEPUTY_COMM_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfOfficersAndStaffOfDivisionalCommAndDeputyCommPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Division> divisions = censusDataEntryService.getAllDivision();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfDivisionalCommAndDeputyComm()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("6.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF DIV. COMM AND DY. COM OFFICES (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (Division division : divisions) {
                if (divisions != null && !divisions.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClassWiseDivisionalCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = division.getName();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfDivisionalCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr _ngcontent-c11=''>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td _ngcontent-c11=''>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td _ngcontent-c11=''>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfDivisionalCommAndDeputyComm());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfOfficersAndStaffOfDivisionalCommAndDeputyComm() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS1_OFFICER_OF_DIV_COMM_DEP_COMM_BY_PAYSCALE_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClass1OfficerOfDivCommDepCommByPayscalePdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Object[]> mainData = new ArrayList<>();
        List<Division> divisions = censusDataEntryService.getAllDivision();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClass1OfficerOfDivCommDepCommByPayscale()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("10.STATISTICS OF CLASS-I OFFICERS OF DIV. COMM AND DY. COM OFFICES BY NATIONAL PAY SCALE (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.78000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.66000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.56500").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.50000").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("TK.43000").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (Division division : divisions) {
                if (divisions != null && !divisions.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClass1OfficerOfDivCommDepCommByPayscaleByName(division.getName(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = division.getName();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClass1OfficerOfDivCommDepCommByPayscaleByParentName(division.getName(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr _ngcontent-c11=''>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td _ngcontent-c11=''>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td _ngcontent-c11=''>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClass1OfficerOfDivCommDepCommByPayscale());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClass1OfficerOfDivCommDepCommByPayscale() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_OFFICERS_AND_STAFF_OF_AUTONOMOUS_BODIES_CORPORATION_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporationForPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporation()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("7.CLASS-WISE STATISTICS OF OFFICERS AND STAFF OF AUTONOMOUS BODIES AND CORPORATIONS (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Sanction").append("</th>");
                stringBuilder.append("<th>").append("Exist").append("</th>");
                stringBuilder.append("<th>").append("Vacant").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getClassWiseStatisticsOfAutonomousBodiesCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfClassWiseStatisticsOfAutonomousBodiesCorporation(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr _ngcontent-c11=''>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td _ngcontent-c11=''>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td _ngcontent-c11=''>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporation());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfOfficersAndStaffOfAutonomousBodiesCorporation() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_MINISTRY_AND_DIVISION_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivisionForPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);

        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivision()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("13.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF OF MINISTRIES AND DIVISION (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Male").append("</th>");
                stringBuilder.append("<th>").append("Female").append("</th>");
                stringBuilder.append("<th>").append("Total").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfMinistryDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfMinistryDivisionByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivision());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffMinistryAndDivision() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_AUTONOMOUS_BODIES_CORPORATION_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporationForPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporation()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("16.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF AUTONOMOUS BODIES CORPORATION (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Male").append("</th>");
                stringBuilder.append("<th>").append("Female").append("</th>");
                stringBuilder.append("<th>").append("Total").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfAutomousAndCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfAutomousAndCorporationByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporation());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffAutonomousBodiesCorporation() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DIVISIONAL_COMMISSIONER_AND_DEPUTY_COMMISSIONER_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDivisionalCommAndDeputyCommForPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<Division> divisions = censusDataEntryService.getAllDivision();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDivisionalCommAndDeputyComm()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("15.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF DIV. COM & DY.COM OFFICES  (" + censusPeriod.getCensusPeriod() + ") ").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Male").append("</th>");
                stringBuilder.append("<th>").append("Female").append("</th>");
                stringBuilder.append("<th>").append("Total").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (Division division : divisions) {
                if (divisions != null && !divisions.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfDivCommDeputyCommByParentNameByParentName(division.getName(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = division.getName();
                        for (int i = 2; i < 17; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleDivCommDeputyCommByParentName(division.getName(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDivisionalCommAndDeputyComm());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffDivisionalCommAndDeputyComm() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @GetMapping(value = REPORT + CLASS_WISE_STATISTICS_OF_MALE_AND_FEMALE_OFFICER_AND_STAFF_DEPARTMENT_AND_DIRECTORATE_FOR_PDF, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> createClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorateForPdfReport(@RequestParam("censusPeriodId") Long censusPeriodId) throws IOException {
        List<Object[]> mainData = new ArrayList<>();
        CensusPeriod censusPeriod = censusPeriodService.getCensusPeriodById(censusPeriodId);
        List<CensusOrganization> reportTOs = censusDataEntryService.getAllCesnusOrganization();
        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorate()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><body>");
            stringBuilder.append("<center><h2>").append("14.CLASS-WISE STATISTICS OF MALE AND FEMALE OFFICER AND STAFF OF DEPARTMENT AND DIRECTORATES (" + censusPeriod.getCensusPeriod() + ")").append("</h2></center>");
            stringBuilder.append("<table class='table_report' border='2px'  style='border-collapse: collapse;width:100%'><tr>");
            stringBuilder.append("<th rowspan='3' align='center'>").append("Ministries And Divisions").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-I").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-II").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-III").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("CLASS-IV").append("</th>");
            stringBuilder.append("<th rowspan='2' align='center' colspan='3'>").append("Total").append("</th>");
            stringBuilder.append("</tr>");
            stringBuilder.append("<tr>");
            for (int i = 0; i < 5; i++) {
                stringBuilder.append("<th>").append("Male").append("</th>");
                stringBuilder.append("<th>").append("Female").append("</th>");
                stringBuilder.append("<th>").append("Total").append("</th>");
            }
            stringBuilder.append("</tr>");

            for (CensusOrganization reportTO : reportTOs) {
                if (reportTOs != null && !reportTOs.isEmpty()) {
                    List<Object[]> lists = censusDataEntryService.getMaleAndFemaleOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                    if (lists != null && !lists.toString().equalsIgnoreCase("null")) {
                        Object[] obj = new Object[17];
                        obj[1] = reportTO.getNameEng();
                        for (int i = 2; i < 176; i++) {
                            obj[i] = " ";
                        }
                        lists.add(0, obj);
                        mainData.addAll(lists);
                    }
                }
                List<Object[]> objects = censusDataEntryService.getTotalOfMaleAndFemaleOfDepartmentAndDirectorateByParentName(reportTO.getNameEng(), censusPeriod.getCensusPeriod());
                if (objects != null && !objects.toString().equalsIgnoreCase("null")) {
                    for (Object[] obj : objects) {
                        obj[1] = "Total";
                    }
                    mainData.addAll(objects);
                }
            }

            if (mainData != null && !mainData.isEmpty()) {
                for (Object[] reportObject : mainData) {
                    stringBuilder.append("<tr>");
                    int i = 0;
                    for (Object rowObject : reportObject) {
                        if (i > 0) {
                            if (rowObject != null && !rowObject.toString().equalsIgnoreCase("null")) {
                                stringBuilder.append("<td>").append(rowObject.toString()).append("</td>");
                            } else {
                                stringBuilder.append("<td>").append("0").append("</td>");
                            }
                        }
                        i++;
                    }
                    stringBuilder.append("</tr>");
                }
            }
            stringBuilder.append("</table>");
            stringBuilder.append("</body></html>");

            document.setPageSize(PageSize.A4.rotate());
            document.open();
            document.add(new Chunk(""));
            ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().replace("null", " 0 ").getBytes());
            byte[] bytes = IOUtils.toByteArray(is);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ReportDataTO reportDataTO = new ReportDataTO();
        InputStream is = new FileInputStream(readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorate());
        reportDataTO.setByteArrays(IOUtils.toByteArray(is));
        return ResponseEntity.ok(reportDataTO);

    }

    private File readPdfClassWiseStaticsOfMaleAndFemaleOfficerAndStaffOfDepartmentAndDirectorate() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        userDir = userDir.replace('\\', '/');
        File file = new File(userDir.replace(";", "\\") + File.separator + "blank.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
