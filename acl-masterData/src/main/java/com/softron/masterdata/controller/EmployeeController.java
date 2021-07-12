package com.softron.masterdata.controller;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softron.common.businessobjects.Response;
import com.softron.common.utils.ResponseUtils;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.service.EmployeeService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Employee.ROOT)
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	@Autowired
	private UserDetailsService userDetailsService;

	// @PostMapping(UrlConstants.Employee.GET_ALL)
	@PostMapping(value = UrlConstants.Employee.GET_ALL)
	public Response create(@RequestParam(value = "employeeDtoString", required = false) String employeeDtoString,
			final Principal principal, @RequestParam(value = "file", required = false) final MultipartFile[] files)
			throws IOException {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		EmployeeDto employeeDto = new ObjectMapper().readValue(employeeDtoString, EmployeeDto.class);
		return employeeService.create(employeeDto, user.getOrgId(), files);
	}
	
	@PostMapping(value = UrlConstants.Employee.EMPLOYEE_CREATE_FROM_EXCEL)
	public Response create(final Principal principal, @RequestParam(value = "file", required = false) final MultipartFile[] files)
			throws IOException {
		
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return employeeService.employeeCreateFromExcel(user.getOrgId(),files);
	}

	@GetMapping(UrlConstants.Employee.GET_ALL)
	public Response getAll(final Principal principal,@RequestParam(value = "type", required = false) String type) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		
		if(type == null){
			return employeeService.getAll(user.getOrgId());
			
		}else{	
			if(type.equals("mobile")){
				return employeeService.getAllForMobile(user.getOrgId());
			}else{
				return ResponseUtils.getSuccessResponse(HttpStatus.OK, "Wrong Parameter Value",String.format("All %ses", "Employee"));
			}
			
		}		
	}

	@GetMapping(UrlConstants.Employee.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return employeeService.get(id);
	}

	@DeleteMapping(UrlConstants.Employee.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return employeeService.delete(id);
	}

	@GetMapping(UrlConstants.Employee.EMPLOYEEPROFILEINFO)
	public Response getEmployeeProfileInfo(@RequestParam("id") Long id) {
		return employeeService.getEmployeeProfileInfo(id);
	}

	@GetMapping(UrlConstants.Employee.EMPLOYEEPROFILECHART)
	public Response getEmployeeProfileChart(@RequestParam("id") Long id) {
		return employeeService.getEmployeeProfileChart(id);
	}

	@GetMapping(UrlConstants.Employee.EMPLOYEE_REPORT)
	public Response getEmployeeReport(@PathVariable(value = "formate") String formate) {
		return employeeService.getEmployeeReport(formate);
	}

	@PutMapping(UrlConstants.Employee.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@RequestParam(value = "employeeDtoString", required = false) String employeeDtoString,
			@RequestParam(value = "file", required = false) final MultipartFile[] files)
			throws JsonParseException, JsonMappingException, IOException {
		
		EmployeeDto employeeDto = new ObjectMapper().readValue(employeeDtoString, EmployeeDto.class);
		return employeeService.update(id, employeeDto, files);
	}
	
	@GetMapping(UrlConstants.Employee.GET_ALL_CO_WORKER_CS)
	public Response getAllCoWorkerCs(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		
		return employeeService.getAllCoWorkerCs(user.getOrgId());		
	}
}
