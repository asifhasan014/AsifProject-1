package com.softron.masterdata.home.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.masterdata.home.service.HomeService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Client.ROOT)
public class HomeController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@GetMapping(UrlConstants.Home.GET_THIS_YEAR_PRODUCTION)
	public Response getHomeYearProduction(final Principal principal,@RequestParam("dataSource") int dataSource) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getHomeYearProduction(user.getOrgId(), dataSource);
	}
	
	@GetMapping(UrlConstants.Home.GET_THIS_YEAR_PRODUCTION_BAR)
	public Response getHomeYearProductionBar(final Principal principal,@RequestParam("dataSource") int dataSource) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getHomeYearProductionBar(user.getOrgId(), dataSource);
	}
	
	@GetMapping(UrlConstants.Home.GET_THIS_YEAR_PRODUCTION_BUYER)
	public Response getHomeYearProductionBuyer(final Principal principal,@RequestParam("dataSource") int dataSource) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getHomeYearProductionBuyer(user.getOrgId(), dataSource);
	}
	//this month production
	@GetMapping(UrlConstants.Home.GET_THIS_MONTH_PRODUCTION)
	public Response getHomeMonthProduction(final Principal principal,@RequestParam("dataSource") int dataSource,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getHomeMonthProduction(user.getOrgId(), dataSource,startDate,endDate);
	}
	
	@GetMapping(UrlConstants.Home.GET_THIS_MONTH_PROGRESS)
	public Response getHomeMonthProgress(final Principal principal,@RequestParam("dataSource") int dataSource,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getHomeMonthProgress(user.getOrgId(), dataSource,startDate,endDate);
	}
	
	@GetMapping(UrlConstants.Home.GET_THIS_MONTH_PRODUCTION_BUYER)
	public Response getHomeMonthProductionBuyer(final Principal principal,@RequestParam("dataSource") int dataSource,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getHomeMonthProductionBuyer(user.getOrgId(), dataSource,startDate,endDate);
	}
	
	@GetMapping(UrlConstants.Home.GET_THIS_MONTH_EFFICIENCY)
	public Response getMonthEfficiency(final Principal principal,@RequestParam("dataSource") int dataSource,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getMonthEfficiency(user.getOrgId(), dataSource,startDate,endDate);
	}
	
	@GetMapping(UrlConstants.Home.GET_THIS_MONTH_DHU)
	public Response getMonthDhu(final Principal principal,@RequestParam("dataSource") int dataSource,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return homeService.getMonthDhu(user.getOrgId(), dataSource,startDate,endDate);
	}

}
