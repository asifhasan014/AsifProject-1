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
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.DepartmentDto;
import com.softron.masterdata.service.DefectService;
import com.softron.masterdata.service.DepartmentService;

@ApiController
@RequestMapping(UrlConstants.Department.ROOT)
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Department.GET_ALL)
	public Response create(@Valid @RequestBody DepartmentDto departmentDto) {
		return departmentService.create(departmentDto);
	}

	@GetMapping(UrlConstants.Department.GET_ALL)
	public Response getAll() {
		
		return departmentService.getAll();
	}

	@GetMapping(UrlConstants.Department.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return departmentService.get(id);
	}

	@DeleteMapping(UrlConstants.Department.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return departmentService.delete(id);
		
	}

	@PutMapping(UrlConstants.Department.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody DepartmentDto departmentDto) {

		return departmentService.update(id, departmentDto);
	}
}
