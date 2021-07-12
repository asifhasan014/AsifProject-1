package com.softron.masterdata.controller;

import java.security.Principal;
import java.sql.Date;

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
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.dto.TargetAndManpowerDto;
import com.softron.masterdata.service.LineTargetAndManPowerService;
import com.softron.masterdata.service.StyleService;
import com.softron.masterdata.service.TargetAndManpowerService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.TargetAndManpower.ROOT)
public class TargetAndManpowerController {
  
	@Autowired
	TargetAndManpowerService targetAndManpowerService;
	
	@Autowired
	LineTargetAndManPowerService lineTargetAndManPowerService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.TargetAndManpower.GET_ALL)
	public Response create(@Valid @RequestBody TargetAndManpowerDto targetAndManpowerDto) {
		return targetAndManpowerService.create(targetAndManpowerDto);
	}

	@GetMapping(UrlConstants.TargetAndManpower.GET_ALL)
	public Response getAll() {
		
		return targetAndManpowerService.getAll();
	}
	
	@GetMapping(UrlConstants.TargetAndManpower.GET_All_BETWEEN_TWO_DATE)
	public Response getByStartDateAndEndDate(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		return lineTargetAndManPowerService.getByStartDateAndEndDate(orgId, startDate, endDate);
	}

	@GetMapping(UrlConstants.TargetAndManpower.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return targetAndManpowerService.get(id);
	}

	@DeleteMapping(UrlConstants.TargetAndManpower.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return targetAndManpowerService.delete(id);
	}

	@PutMapping(UrlConstants.TargetAndManpower.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody TargetAndManpowerDto targetAndManpowerDto) {
		return targetAndManpowerService.update(id, targetAndManpowerDto);
	}
	
}
