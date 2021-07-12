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
import com.softron.schema.admin.entity.masterdata.SubSection;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.WorkProcessDto;
import com.softron.masterdata.service.SubSectionService;
import com.softron.masterdata.service.WorkProcessService;

@ApiController
@RequestMapping(UrlConstants.WorkProcess.ROOT)
public class WorkProcessController {
	@Autowired
	WorkProcessService workProcessService;

	@Autowired
    private UserDetailsService userDetailsService;
	
	@PostMapping(UrlConstants.WorkProcess.GET_ALL)
	public Response create(@Valid @RequestBody WorkProcessDto workProcessDto,final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return workProcessService.create(workProcessDto,user.getOrgId());
	}

	@GetMapping(UrlConstants.WorkProcess.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return workProcessService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.WorkProcess.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return workProcessService.get(id);
	}

	@DeleteMapping(UrlConstants.WorkProcess.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return workProcessService.delete(id);
		
	}

	@PutMapping(UrlConstants.WorkProcess.GET)
	public Response updateWorkProcess(@PathVariable(value = "id") Long id,
			@Valid @RequestBody WorkProcessDto workProcessDto) {

		return workProcessService.update(id, workProcessDto);
	}
}
