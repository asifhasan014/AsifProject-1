package com.softron.production.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.softron.production.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.quality.service.QualityDefectService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Production.ROOT)
public class ProductionController {

	@Autowired
	ProductionService productionService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@GetMapping(UrlConstants.Production.GET_ALL)
	public Response lineWiseAllProduction() {
		return productionService.getAll();
	}

	@GetMapping(UrlConstants.Production.P_BAR_CHART)
	public Response productionBarChart(@RequestParam("endDate") String endDate) {
		return productionService.getProductionBarChart(endDate);
	}

	@GetMapping(UrlConstants.Production.P_HOURLY_GRAPH)
	public Response productionHourlyGraph(@RequestParam("endDate") String endDate) {
		return productionService.getProductionHourlyGraph(endDate);
	}

	@GetMapping(UrlConstants.Production.GET_PRODUCTIONREPORT)
	public Response getProductionReport(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		return productionService.getProductionReport(startDate,endDate);
	}

	@GetMapping(UrlConstants.Production.GET_PRODUCTION_REPORT_QC_PASS)
	public Response getProductionReportQcPass(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("orgId") List<Long> orgId,@RequestParam(required = false) List<String> orderEntityList) {
		//with organization
		if(orderEntityList != null) {
			return productionService.getProductionReportQcPass(startDate,endDate,orgId,orderEntityList);
		}else {
			orderEntityList = null;
			return productionService.getProductionReportQcPass(startDate,endDate,orgId,orderEntityList);
		}
		

	}

	@GetMapping(UrlConstants.Production.P_DASHBOARD)
	public Response getProductionDashBoard(@RequestParam("currentDateAndTime") String currentDateAndTime) {
		return productionService.getProductionDashBoard(currentDateAndTime);
	}

	@GetMapping(UrlConstants.Production.P_DASHBOARD_QC_PASS)
	public Response getProductionDashBoardQcPass(@RequestParam("currentDateAndTime") String currentDateAndTime,@RequestParam("orgId") Long orgId) {
		return productionService.getProductionDashBoardQCPass(currentDateAndTime,orgId);
	}
	
	@GetMapping(UrlConstants.Production.LINE_WISE_PRODUCTION_CHART)
	public Response getLineWiseProductionChart(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate) {
		return productionService.getLineWiseProductionChart(orgId,startDate);
	}
	
	@GetMapping(UrlConstants.Production.LINE_WISE_HOURLY_PRODUCTION_CHART)
	public Response getLineWiseHourlyProductionChart(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate) {
		return productionService.getLineWiseHourlyProductionChart(orgId,startDate);
	}
	@GetMapping(UrlConstants.Production.OPERATION_ANALYSIS_COUTER_DEVICE)
	public Response getOperationAnalysisCd(@RequestParam("minRange") double minRange,@RequestParam("maxRange") double maxRange) {
		return productionService.getOperationAnalysisCd(minRange,maxRange);
	}
	@GetMapping(UrlConstants.Production.EMPLOYEE_PERFORMED_PROCESS_LIST)
	public Response getEmployeePerformedProcessList(@RequestParam("employeeId") Long employeeId,@RequestParam("minRange") double minRange,@RequestParam("maxRange") double maxRange) {
		return productionService.getEmployeePerformedProcessList(employeeId,minRange,maxRange);
	}
	@GetMapping(UrlConstants.Production.EMPLOYEE_PROCESS_WISE_CYCLE_TIME_TREND)
	public Response getEmployeeProcessWiseCycleTimeTrend(@RequestParam("employeeId") Long employeeId,@RequestParam("operationId") Long operationId,@RequestParam("minRange") double minRange,@RequestParam("maxRange") double maxRange) {
		return productionService.getEmployeeProcessWiseCycleTimeTrend(employeeId,operationId,minRange,maxRange);
	}
	
	@GetMapping(UrlConstants.Production.HOURLY_PRODUCTION_AND_DHU_MONITORING_BOARD)
	public Response getHourlyProductionAndDhuMonitoringBoard(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return productionService.getHourlyProductionAndDhuMonitoringBoard(user.getOrgId());
	}
	
	@GetMapping(UrlConstants.Production.PRODUCTION_DETAILS_REPORT)
	public Response getProductionDetailsReport(@RequestParam("orderId") Long orderId,@RequestParam("option") String option) {
		return productionService.getProductionDetailsReport(orderId,option);
	}
	
	@GetMapping(UrlConstants.Production.PRODUCTION_DETAILS_GRAPH_REPORT)
	public Response getProductionDetailsGraphReport(@RequestParam("orderId") Long orderId) {
		return productionService.getProductionDetailsGraphReport(orderId);
	}
}
