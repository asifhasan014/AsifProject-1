package com.softron.production.controller;

import java.security.Principal;

import javax.validation.Valid;

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
import com.softron.production.dto.ReportLayoutDto;
import com.softron.production.service.ReportLayoutService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Production.ROOT)
public class ReportLayoutController {
	
	@Autowired
	ReportLayoutService reportLayoutService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.ReportLayout.GET_ALL)
	public Response create(@Valid @RequestBody ReportLayoutDto reportLayoutDto) {
		return reportLayoutService.create(reportLayoutDto);
	}
	
	@GetMapping(UrlConstants.ReportLayout.GET_ALL)
	public Response getAll() {
		return reportLayoutService.getAll();
	}
	
	@GetMapping(UrlConstants.ReportLayout.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return reportLayoutService.get(id);
	}
	
	@GetMapping(UrlConstants.ReportLayout.GET_BY_TYPE)
	public Response getAllByType(@RequestParam("type") String type) {
		return reportLayoutService.getAllByType(type);
	}
	
	@DeleteMapping(UrlConstants.ReportLayout.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return reportLayoutService.delete(id);
	}
	
	@PutMapping(UrlConstants.ReportLayout.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody ReportLayoutDto reportLayoutDto) {
		return reportLayoutService.update(id, reportLayoutDto);
	}
	
	
}
