package com.softron.quality.controller;


import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.softron.production.repository.ReportLayoutRepository;
import com.softron.quality.dto.QualityTransactionDto;
import com.softron.quality.service.QualityTransactionReportShareService;
import com.softron.quality.service.QualityTransactionService;
import com.softron.schema.admin.entity.production.ReportLayout;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.QualityTransaction.ROOT)
public class QualityTransactionController {

	@Autowired
	QualityTransactionService qualityTransactionService;
	
	@Autowired
	QualityTransactionReportShareService qualityTransactionReportShareService;
	
	@Autowired
	ReportLayoutRepository reportLayoutRepository;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.QualityTransaction.GET_ALL)
	public Response create(@Valid @RequestBody List<QualityTransactionDto> qualityTransactionDto,final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return qualityTransactionService.create(qualityTransactionDto,user.getUsername());
	}

	@GetMapping(UrlConstants.QualityTransaction.GET_ALL)
	public Response getAll() {
		return qualityTransactionService.getAll();
	}

	@GetMapping(UrlConstants.QualityTransaction.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return qualityTransactionService.get(id);
	}
	
	//SimpleDateFormat formatter5=new SimpleDateFormat(" MM/dd/yyyy HH:mm:ss"); 
	
	@GetMapping(UrlConstants.QualityTransaction.GET_SUM)
	public Response getCheck(@RequestParam("startDate") String startDates,@RequestParam("endDate")String endDates,@RequestParam("orgId")Long orgId) {
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter.parse(startDates);
		    endDate = formatter.parse(endDates);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return qualityTransactionService.totalCheck(startDate,endDate,orgId);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_TREND)
	public Response getTrends(@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate,@RequestParam("timeUnit")String timeUnit,@RequestParam("orgId")Long orgId) {
		
		return qualityTransactionService.getTrends(startDate, endDate, timeUnit,orgId);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_TRENDALLSEC)
	public Response getTrendsGraphValue(@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate,@RequestParam("timeUnit")String timeUnit) {
		
		return qualityTransactionService.getTrendsGraphValue(startDate, endDate, timeUnit);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_QUALITYTRENDALLSEC)
	public Response getQualityTrendsGraph(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate,@RequestParam("timeUnit")String timeUnit) {
//		public Response getQualityTrendsGraph(@RequestParam("graphType") String graphType,@RequestParam("typeId") String typeId,@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate,@RequestParam("timeUnit")String timeUnit) {
		
//		return qualityTransactionService.getQualityTrendsGraph(graphType,typeId,startDate, endDate, timeUnit);
		return qualityTransactionService.getQualityTrendsGraph(orgId,startDate, endDate, timeUnit);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_BARCHART)
	public Response getBarChatValue(@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate) {
		
		return qualityTransactionService.getBarChartValue(startDate, endDate);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_QUALITYBARCHART)
//	public Response getQualityBarChart(@RequestParam("graphType") String graphType,@RequestParam("typeId") String typeId,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		public Response getQualityBarChart(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {	
//		return qualityTransactionService.getQualityBarChart(graphType, typeId, startDate, endDate);
		return qualityTransactionService.getQualityBarChart(orgId,startDate, endDate);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_TOPFIVEQUALITYDEFECT)
	public Response getTopFiveQualityDefect() {
		
		return qualityTransactionService.getTopFiveQualityDefect();
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_TOPDEFECTIVEOPERATION)
	public Response getTopDefectiveOperation(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		
		return qualityTransactionService.getTopDefectiveOperation(orgId,startDate,endDate);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_TSL)
	public Response getTLSdashBoard(@RequestParam("orgId") Long orgId) {
		return qualityTransactionService.getTLSdashBoard(orgId);
	}
	

	@DeleteMapping(UrlConstants.QualityTransaction.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return qualityTransactionService.delete(id);
	}

	@PutMapping(UrlConstants.QualityTransaction.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody QualityTransactionDto qualityTransactionDto) {

		return qualityTransactionService.update(id, qualityTransactionDto);
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_QCPASSREPORT)
	public Response getQcPassReport(@RequestParam("orgId") List<Long> orgId,@RequestParam(required = false) List<String> orderEntityList,@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate,@RequestParam("timeUnit")String timeUnit) {
		
		if(orderEntityList != null) {
			return qualityTransactionService.getQCPassReport(orgId,orderEntityList,startDate, endDate, timeUnit);
		}else {
			orderEntityList = null;
			return qualityTransactionService.getQCPassReport(orgId,orderEntityList,startDate, endDate, timeUnit);
		}
		
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_DEFECTANALYSISREPORT)
	public Response getDefectAnalysisReport(@RequestParam("orgId") List<Long> orgId,@RequestParam(required = false) List<String> orderEntityList,@RequestParam("startDate") String startDate,@RequestParam("endDate")String endDate,@RequestParam("timeUnit")String timeUnit) {
		if(orderEntityList != null) {
			return qualityTransactionService.getDefectAnalysisReport(orgId,orderEntityList,startDate, endDate, timeUnit);
		}else {
			orderEntityList = null;
			return qualityTransactionService.getDefectAnalysisReport(orgId,orderEntityList,startDate, endDate, timeUnit);
		}
		
	}
	
	@GetMapping(UrlConstants.QualityTransaction.GET_REPORTSHARE)
	public Response getReportShare(@RequestParam("reportLayoutId") Long reportLayoutId) {
		
		Optional<ReportLayout> reportLayoutOpt = reportLayoutRepository.findById(reportLayoutId);
		ReportLayout report = reportLayoutOpt.get();
		String reportType = report.getType();
		
		if(reportType.equals("/quality/qc-pass")) {
			return qualityTransactionReportShareService.getQcPassReportShare(reportLayoutId);
		}else {
			return qualityTransactionReportShareService.getProductionReportShare(reportLayoutId);
		}
		
	}

	@GetMapping(UrlConstants.QualityTransaction.GET_LINE_WISE_ORDER_INFO)
	public Response getLineWiseOrderInfo(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return qualityTransactionService.getLineWiseOrderInfo(user.getOrgId());
		
	}
}
