package com.softron.masterdata.capacitystudy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.softron.masterdata.capacitystudy.dto.CapacityStudyDto;
import com.softron.masterdata.capacitystudy.service.CapacityStudyService;
import com.softron.masterdata.dto.ClientDto;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Client.ROOT)
public class CapacityStudyController {

	@Autowired
	CapacityStudyService CapacityStudyService;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.CapacityStudy.GET_ALL)
	public Response create(@Valid @RequestBody List<CapacityStudyDto> capacityStudyDtoList, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.create(capacityStudyDtoList, user.getUsername());
	}

	@PutMapping(UrlConstants.CapacityStudy.GET_ALL)
	public Response update(@Valid @RequestBody List<CapacityStudyDto> capacityStudyDtoList,final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.update(capacityStudyDtoList, user.getUsername());
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_CS_BY_STATUS_IS_COMPLETE)
	public Response getAllByStatusIsComplete(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.getAllByStatusIsComplete(user.getOrgId());
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_CS_BY_STATUS_IS_PARTIAL)
	public Response getAllByStatusIsPartial(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.getAllByStatusIsPartial(user.getUsername());
	}
	
	@GetMapping(UrlConstants.CapacityStudy.GET_INCOMPLETE_CS_LIST)
	public Response getInCompleteCsList(final Principal principal) {
			final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
			return CapacityStudyService.getInCompleteCsList(user.getUsername());
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_RESUMELIST)
	public Response getResumeList(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.getResumeList(user.getUsername());
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_CAPACITY_GRAPH_DATA)
	public Response getCapacityGraphData(@PathVariable(value = "capacityId") Long capacityId) {
		return CapacityStudyService.getCapacityGraphData(capacityId);
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_CAPACITY_REPORT_DATA)
	public Response getCapacityReportData(@PathVariable(value = "capacityId") Long capacityId) {
		return CapacityStudyService.getCapacityReportData(capacityId);
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_EMPLOYEE_PROFILE)
	public Response getEmployeeProfileFromCapacityStudy(@PathVariable(value = "employeeId") Long employeeId) {
		return CapacityStudyService.getEmployeeProfileFromCapacityStudy(employeeId);
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_OPERATION_ANALYSIS_CS)
	public Response getOperationAnalysisCS(@RequestParam("orgId") List<Long> orgId,@RequestParam(required = false) List<String> orderEntityList) {
		if(orderEntityList != null) {
			return CapacityStudyService.getOperationAnalysisCS(orgId,orderEntityList);
		}else {
			orderEntityList = null;
			return CapacityStudyService.getOperationAnalysisCS(orgId,orderEntityList);
		}
		
	}

	@GetMapping(UrlConstants.CapacityStudy.GET_OPERATION_ANALYSIS_TREND)
	public Response getOperatorCycleTimeTrends(@RequestParam("employeeId") Long employeeId,
			@RequestParam("operationId") Long operationId, @RequestParam("styleId") Long styleId) {
		return CapacityStudyService.getOperatorCycleTimeTrends(employeeId, operationId, styleId);
	}

	@GetMapping(UrlConstants.CapacityStudy.WORK_STUDY_CAPACITY_GRAPH)
	public Response getCapacityStudyGraph(@RequestParam("capacityStudyId") Long capacityStudyId) {
		return CapacityStudyService.getCapacityStudyGraph(capacityStudyId);
	}
	
	@GetMapping(UrlConstants.CapacityStudy.WORK_STUDY_CAPACITY_GRAPH_2)
	public Response getCapacityStudyGraph2(@RequestParam("capacityStudyId") Long capacityStudyId) {
		return CapacityStudyService.getCapacityStudyGraph2(capacityStudyId);
	}
	
	@GetMapping(UrlConstants.CapacityStudy.GET_NEWS_FEED)
	public Response getNewsFeed(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.getNewsFeed(user.getOrgId());
	}
	
	@GetMapping(UrlConstants.CapacityStudy.GET_NEWS_FEED_HEADER_INFO)
	public Response getNewsFeedHeaderInfo(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return CapacityStudyService.getNewsFeedHeaderInfo(user.getOrgId());
	}

}