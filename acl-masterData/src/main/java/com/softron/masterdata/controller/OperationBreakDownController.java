package com.softron.masterdata.controller;

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
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.service.OperationBreakDownService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.OperationBreakDown.ROOT)
public class OperationBreakDownController {

	@Autowired
	OperationBreakDownService operationBreakDownService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.OperationBreakDown.GET_ALL)
	public Response create(@Valid @RequestBody OperationBreakDownDto operationBreakDownDto) {
		return operationBreakDownService.create(operationBreakDownDto);
	}

	@GetMapping(UrlConstants.OperationBreakDown.GET_ALL)
	public Response getAll() {
		return operationBreakDownService.getAll();
	}

	@GetMapping(UrlConstants.OperationBreakDown.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return operationBreakDownService.get(id);
	}
	
	@GetMapping(UrlConstants.OperationBreakDown.GET_OPBDBYSTYLE)
	public Response getOperationBreakDownByStyleId(@RequestParam("styleId") Long styleId,@RequestParam("org_id") Long org_id) {
		return operationBreakDownService.getOperationBreakDownByStyleId(styleId, org_id);
	}
	

	@DeleteMapping(UrlConstants.OperationBreakDown.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return operationBreakDownService.delete(id);
	}

	@PutMapping(UrlConstants.OperationBreakDown.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody OperationBreakDownDto operationBreakDownDto) {
		return operationBreakDownService.update(id, operationBreakDownDto);
	}
	
}
