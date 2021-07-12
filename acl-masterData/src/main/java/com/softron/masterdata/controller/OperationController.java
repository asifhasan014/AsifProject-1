package com.softron.masterdata.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.service.EmployeeService;
import com.softron.masterdata.service.OperationService;

@ApiController
@RequestMapping(UrlConstants.Operation.ROOT)
public class OperationController {
	@Autowired
	OperationService operationService;

	@Autowired
    private UserDetailsService userDetailsService;

	
	@PostMapping(UrlConstants.Operation.GET_ALL)
	public Response create(@Valid @RequestBody OperationDto operationDto, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return operationService.create(operationDto, user.getOrgId());
	}

	@GetMapping(UrlConstants.Operation.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return operationService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.Operation.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return operationService.get(id);
	}

	@DeleteMapping(UrlConstants.Operation.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return operationService.delete(id);
		
	}

	@PutMapping(UrlConstants.Operation.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody OperationDto operationDto) {

		return operationService.update(id, operationDto);
	}
}
